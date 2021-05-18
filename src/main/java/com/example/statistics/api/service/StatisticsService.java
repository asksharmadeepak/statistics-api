package com.example.statistics.api.service;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface StatisticsService {

    CompletableFuture<HttpStatus> createRawDataStatistics(Resource resource) throws IOException;

    CompletableFuture<String> getStatisticsPayload() throws IOException;

}
