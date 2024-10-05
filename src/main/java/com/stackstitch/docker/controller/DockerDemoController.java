package com.stackstitch.docker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/docker")
public class DockerDemoController {

    @GetMapping("/testAPI")
    public String  getTestAPI(){
            return new String("Docker Test API is working");
    }
}
