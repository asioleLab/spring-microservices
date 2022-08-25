package course.spring.microservices.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.Servlet;
import java.net.URI;
import java.util.List;

@RestController
public class UserResource {

    private UserDaoService service;

    public UserResource(UserDaoService service) {
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
    public User createUser(@RequestBody User user){
         return service.save(user);
    }

    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody User user){
        User savedUser=service.saveUser(user);
        // /user/4 => /user/{id} => user.getID
        URI location= ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

}
