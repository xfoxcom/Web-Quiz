package engine;

import org.springframework.stereotype.Component;

@Component
public class UserRepo {
UserRepository userRepository;
public UserRepo (UserRepository userRepository) {
    this.userRepository = userRepository;
}
public User findUserByEmail (String email) {
return userRepository.findById(email).orElse(new User("noSuchEmail@goodBye.com"));
}
public void save (User user) {
    userRepository.save(user);
}
public boolean userExist (String email) {
    return userRepository.existsById(email);
}
}
