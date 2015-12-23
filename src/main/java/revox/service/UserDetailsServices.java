package revox.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import revox.repository.userRepository;

/**
 * Created by ashraf on 8/2/15.
 */
@Service
@Transactional
public class UserDetailsServices implements UserDetailsService {
    @Autowired
    userRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).
                map(user -> new User(user.getEmail(), user.getPassword(),
                        user.grantedAuthorities())).
                orElseThrow(() -> new UsernameNotFoundException("not found "));


    }

}
