package am.itspace.companycmployeespring.service;

import am.itspace.companycmployeespring.entity.User;
import am.itspace.companycmployeespring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;


    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findByUserEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveUser(User user) {
        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        if (!byEmail.isPresent()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            mailService.sendEmail(user.getEmail(), "Welcome", "Hi" + user.getName() + "\n" +
                    "You have successfully register ! ! !");
            userRepository.save(user);
        }
    }

    public void userDelete(int userId) {
        userRepository.deleteById(userId);
    }
}
