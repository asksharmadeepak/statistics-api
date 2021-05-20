package com.example.statistics.api.controller;

import com.example.statistics.api.service.StatisticsService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.example.statistics.api.controller.StatisticsRoutes.EVENT;
import static com.example.statistics.api.controller.StatisticsRoutes.STATS;

@RestController
public class StatisticsController {

    protected static final Logger LOGGER = LoggerFactory.getLogger(StatisticsController.class);

    final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @ApiOperation(value = "POST api /event which receive data e.g. 1607341341814,0.0442672968,1282509067 ")
    @PostMapping(EVENT)
    public ResponseEntity createRawDataStatistics(@RequestBody Resource resource) throws IOException, ExecutionException, InterruptedException {
        LOGGER.info("Request received at controller for row data /event {}");
        return new ResponseEntity<>(statisticsService.createRawDataStatistics(resource).get());
    }

    @GetMapping(STATS)
    @ApiOperation(value = "GET /stats which will retrieve the statistics e.g. 0,0.0,0.0,0.0,0.0 ")
    public CompletableFuture<ResponseEntity> getStatistics() throws IOException {
        LOGGER.info("Request received for getStatistics controller for /stats {}");
        return statisticsService.getStatisticsPayload().<ResponseEntity>thenApply(ResponseEntity::ok);
    }

}
