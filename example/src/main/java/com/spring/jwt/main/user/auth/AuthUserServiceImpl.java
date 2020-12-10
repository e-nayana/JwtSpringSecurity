package com.spring.jwt.main.user.auth;

import com.spring.jwt.main.user.User;
import com.spring.jwt.main.user.UserRepository;
import com.spring.security.jwtsecurity.auth_semi.JwtUserDetails;
import com.spring.security.jwtsecurity.auth_semi.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Houston(Nayana)
 **/
@Service("userDetailServiceImpl")
public class AuthUserServiceImpl extends JwtUserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public JwtUserDetails userByUserName(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No dao found with email '%s'.", email));
        } else {
            return AuthUserEngine.AuthUserFactory.create(user);
        }
    }
}
