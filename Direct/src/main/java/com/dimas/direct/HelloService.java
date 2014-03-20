package com.dimas.direct;

import org.springframework.stereotype.Component;

@Component
public class HelloService {
    public String sayHello() {
        return "Hello world!";
    }
}
