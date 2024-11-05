package com.stackstitch.docker.service;

import com.stackstitch.docker.dto.AggregateDto;

import java.util.concurrent.CompletableFuture;

public interface AggregatorService {

    public AggregateDto getAllList() throws Exception;
}
