package com.stackstitch.docker.controller;

import com.stackstitch.docker.dto.AggregateDto;
import com.stackstitch.docker.service.AggregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/aggregate")
public class AggregatorController {

    @Autowired
    AggregatorService aggregatorService;

    @GetMapping
    public ResponseEntity<Object> getAllList() throws Exception{
         return new ResponseEntity<>(aggregatorService.getAllList(), HttpStatus.OK);

    }
}
