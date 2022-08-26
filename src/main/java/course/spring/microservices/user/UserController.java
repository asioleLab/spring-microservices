package course.spring.microservices.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

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

    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id){
        User user=service.findOne(id);
        if(user==null){
            throw new UserNotFoundException("id:" +id);
        }
        return user;
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
