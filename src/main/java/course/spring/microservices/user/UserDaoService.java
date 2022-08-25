package course.spring.microservices.user;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDaoService {

    private static List<User> users = new ArrayList<>();

    private static int usersCount = 0;

    static {
        users.add(new User(++usersCount,"Adam", LocalDate.now().minusYears(30)));
        users.add(new User(++usersCount,"Eve",LocalDate.now().minusYears(25)));
        users.add(new User(++usersCount,"Jim",LocalDate.now().minusYears(20)));
    }

    public List<User> findAll() {
        return users;
    }

    public User findOne(int id){
        Predicate<? super User> predicate= user -> user.getId().equals(id);
        return users.stream().filter(predicate).findFirst().orElse(null);
    }

    public User createUserByNAmeAndBirthDate(String name, LocalDate birthdayDate){
        int size= users.size();
        User newUser= new User(++size,name,birthdayDate);
        users.add(newUser);
        return newUser;
    }

    public User save(User user){
        user.setId(++usersCount);
        users.add(user);
        return user;
    }

    public User saveUser(User user){
        user.setId(++usersCount);
        users.add(user);
        return user;
    }
}
