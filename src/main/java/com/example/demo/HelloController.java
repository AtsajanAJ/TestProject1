package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }
    @GetMapping("/greet")
    public String greetUser(@RequestParam String param1, @RequestParam String param2) {
        return "You entered: " + param1 + " and " + param2;
    }

}
