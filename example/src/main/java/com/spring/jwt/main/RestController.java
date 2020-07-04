package com.spring.jwt.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Houston(Nayana)
 **/
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/rest")
public class RestController {

    @Autowired
    @Qualifier("userDetailServiceImpl")
    UserDetailsService userDetailsService;

    @RequestMapping(method = RequestMethod.GET)
    public UserDetails index(){
        return userDetailsService.loadUserByUsername("yep@gmail.com");
    }
}
