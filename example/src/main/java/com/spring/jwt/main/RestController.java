package com.spring.jwt.main;

import com.spring.jwt.main.redis.RedisUserService;
import com.spring.jwt.main.redis.TestClass;
import org.aspectj.weaver.ast.Test;
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
//@PreAuthorize("hasPermission(#contact, 'admin')")
public class RestController {

    UserDetailsService userDetailsService;
    RedisUserService redisUserService;

    @Autowired
    public RestController(
            @Qualifier("userDetailServiceImpl") UserDetailsService userDetailsService,
            RedisUserService redisUserService

    ) {
        this.userDetailsService = userDetailsService;
        this.redisUserService = redisUserService;
    }



    @RequestMapping(method = RequestMethod.GET)
    public UserDetails index(){
        return userDetailsService.loadUserByUsername("yep@gmail.com");
    }

    @RequestMapping(value = "/redis-test/insert", method = RequestMethod.GET)
    public String redisSave(){

        TestClass testClass = new TestClass();
        testClass.setInteger(1);

        redisUserService.put("test", testClass);

        return "test";
    }

    @RequestMapping(value = "/redis-test/fetch", method = RequestMethod.GET)
    public TestClass redisGet(){
        return redisUserService.get("test");
    }

}
