package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class RegistrationController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    PasswordEncoder encoder;
    @PostMapping("/api/register")
    public void register(@RequestBody User user) {
if(userRepo.userExist(user.getEmail())) {
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is already taken");
} else {
    user.setPassword(encoder.encode(user.getPassword()));
    userRepo.save(user);
}
    }
}
