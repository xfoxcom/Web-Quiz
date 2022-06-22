package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
public class RegistrationController {
    @Autowired
   private UserRepo userRepo;
    @Autowired
   private PasswordEncoder encoder;
    @Autowired
   private AuthorityRepository authorityRepository;
    public RegistrationController (AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }
    @PostMapping("/api/register")
    public void register(@RequestBody User user) {
if(userRepo.userExist(user.getEmail())) {
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is already taken");
} else
if (!user.getEmail().matches(".+@.+\\..+")) {
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong format");
} else if (user.getPassword().length() < 5) {
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong format");
} else {
    user.setPassword(encoder.encode(user.getPassword()));
    Authorities authorities = new Authorities();
    authorities.setUser_email(user.getEmail());
    authorities.setAuth("ROLE_USER");
    userRepo.save(user);
    authorityRepository.save(authorities);

}
    }
}
