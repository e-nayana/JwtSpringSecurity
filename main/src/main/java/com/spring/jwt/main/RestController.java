package com.spring.jwt.main;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author houston-hash
 **/
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/rest")
public class RestController {

    @RequestMapping(method = RequestMethod.GET)
    public String index(){
        return "rest waving you !";
    }
}
