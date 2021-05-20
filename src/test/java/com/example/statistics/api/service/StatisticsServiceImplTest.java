package com.example.statistics.api.service;

import com.example.statistics.api.helper.StatisticsInputValidator;
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
    StatisticsInputValidator mockStatisticsInputValidator;

    @InjectMocks
    StatisticsService statisticsService = new StatisticsServiceImpl(mockStatisticsInputValidator);

    CompletableFuture<HttpStatus> eventualResponseEvent;

    @Before
    public void setUp() {
        eventualResponseEvent = CompletableFuture.supplyAsync(() -> HttpStatus.ACCEPTED);
    }

    @Test
    public void shouldCreateRawDataStatistics() throws IOException, ExecutionException, InterruptedException {
        Mockito.when(mockStatisticsInputValidator.validateInputRowData("0.0442672968", 1282509067))
                .thenReturn(true);
        byte[] byteArray = "1621272364371,0.0442672968,1282509067".getBytes();
        ByteArrayResource resource = new ByteArrayResource(byteArray);
        CompletableFuture<HttpStatus> eventualRawDataStatistics = statisticsService.createRawDataStatistics(resource);
        Assert.assertEquals(eventualRawDataStatistics.get(), HttpStatus.ACCEPTED);
        Mockito.verify(mockStatisticsInputValidator, times(1))
                .validateInputRowData("0.0442672968", 1282509067);
    }

    @Test
    public void shouldGetStatisticsPayload() throws IOException, ExecutionException, InterruptedException {
        CompletableFuture<String> eventualStatisticsPayload = statisticsService.getStatisticsPayload();
        Assert.assertEquals(eventualStatisticsPayload.get(), "INR");
    }
}