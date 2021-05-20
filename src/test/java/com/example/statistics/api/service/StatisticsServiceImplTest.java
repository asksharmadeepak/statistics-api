package com.example.statistics.api.service;

import com.example.statistics.api.helper.StatisticsRowValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
public class StatisticsServiceImplTest {

    @Mock
    StatisticsRowValidator mockStatisticsRowValidator;

    @InjectMocks
    StatisticsService statisticsService = new StatisticsServiceImpl(mockStatisticsRowValidator);

    CompletableFuture<HttpStatus> eventualResponseEvent;
    ByteArrayResource resource;
    @Before
    public void setUp() {
        byte[] byteArray = "1621272364371,0.0442672968,1282509067".getBytes();
        resource = new ByteArrayResource(byteArray);
        eventualResponseEvent = CompletableFuture.supplyAsync(() -> HttpStatus.ACCEPTED);
    }

    @Test
    public void shouldCreateRawDataStatistics() throws IOException, ExecutionException, InterruptedException {
        Mockito.when(mockStatisticsRowValidator.validateInputRowData("0.0442672968", 1282509067))
                .thenReturn(true);
        CompletableFuture<HttpStatus> eventualRawDataStatistics = statisticsService.createRawDataStatistics(resource);
        Assert.assertEquals(eventualRawDataStatistics.get(), HttpStatus.ACCEPTED);
        Mockito.verify(mockStatisticsRowValidator, times(1))
                .validateInputRowData("0.0442672968", 1282509067);
    }

    @Test
    public void shouldGetStatisticsPayload() throws IOException, ExecutionException, InterruptedException {
        statisticsService.createRawDataStatistics(resource);
        CompletableFuture<String> eventualStatisticsPayload = statisticsService.getStatisticsPayload();
        Assert.assertEquals(eventualStatisticsPayload.get(), "0,0.0,0.0,0.0,0.0");
    }
}