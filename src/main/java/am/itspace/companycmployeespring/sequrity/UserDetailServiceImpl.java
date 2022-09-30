package am.itspace.companycmployeespring.sequrity;

import am.itspace.companycmployeespring.entity.User;
import am.itspace.companycmployeespring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byEmail = repository.findByEmail(username);
        if (byEmail.isEmpty()) {
            throw new UsernameNotFoundException("User does not exists");
        }
        return new CurrentUser(byEmail.get());
    }
}
