package com.example.statistics.api.controller;


import com.example.statistics.api.service.StatisticsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    StatisticsService mockStatisticsService;


    CompletableFuture<HttpStatus> eventualResponseEventAccepted;
    CompletableFuture<HttpStatus> eventualResponseEventPartialContent;
    CompletableFuture<String> eventualResponseStats;

    @Before
    public void setUp() {
        eventualResponseEventAccepted = CompletableFuture.supplyAsync(() -> HttpStatus.ACCEPTED);
        eventualResponseEventPartialContent = CompletableFuture.supplyAsync(() -> HttpStatus.PARTIAL_CONTENT);
        eventualResponseStats = CompletableFuture.supplyAsync(() -> "0,0.0,0.0,0.0,0.0");
    }

    @Test
    public void shouldGetStatistics() throws Exception {
        Mockito.when(mockStatisticsService.getStatisticsPayload()).thenReturn(eventualResponseStats);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(StatisticsRoutes.STATS);
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
        Mockito.verify(mockStatisticsService, times(1)).getStatisticsPayload();
    }

    @Test
    public void shouldCreateRawDataStatistics() throws Exception {
        byte[] byteArray = "1621272364371,0.0442672968,1282509067".getBytes();
        ByteArrayResource resource = new ByteArrayResource(byteArray);
        Mockito.when(mockStatisticsService.createRawDataStatistics(resource)).
                thenReturn(eventualResponseEventAccepted);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(StatisticsRoutes.EVENT).content(byteArray);
        mockMvc.perform(requestBuilder).andExpect(status().isAccepted());
        Mockito.verify(mockStatisticsService, times(1)).createRawDataStatistics(resource);
    }

    @Test
    public void shouldCreateRawDataStatisticsWithPartialData() throws Exception {
        byte[] byteArray = "1621272364371,0.04426729689,1282509067".getBytes();
        ByteArrayResource resource = new ByteArrayResource(byteArray);
        Mockito.when(mockStatisticsService.createRawDataStatistics(resource)).
                thenReturn(eventualResponseEventPartialContent);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(StatisticsRoutes.EVENT).content(byteArray);
        mockMvc.perform(requestBuilder).andExpect(status().isPartialContent());
        Mockito.verify(mockStatisticsService, times(1)).createRawDataStatistics(resource);
    }
}