package course.spring.microservices.user;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class UserController {

    private UserDaoService service;

    public UserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }

//    @GetMapping("/users/{id}")
//    public User retrieveUser(@PathVariable int id){
//        User user=service.findOne(id);
//        if(user==null){
//            throw new UserNotFoundException("id:" +id);
//        }
//        return user;
//    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id){
        User user=service.findOne(id);
        if(user==null){
            throw new UserNotFoundException("id:" +id);
        }
        EntityModel<User> entityModel= EntityModel.of(user);
        WebMvcLinkBuilder link= linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-users"));

        return entityModel;
    }

    @PostMapping("/create")
    public User createUser(@Valid @RequestBody User user){
         return service.save(user);
    }

    @PostMapping("/users")
    public ResponseEntity<User> addUser(@Valid @RequestBody User user){
        User savedUser=service.saveUser(user);
        // /user/4 => /user/{id} => user.getID
        URI location= ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/deleteUsers/{id}")
    public void deleteUsers(@PathVariable int id){
        User deleteUser=service.deleteUser(id);
    }

}
