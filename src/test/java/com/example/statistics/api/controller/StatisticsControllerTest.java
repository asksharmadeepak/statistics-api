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

    CompletableFuture<HttpStatus> eventualResponseEvent;

    @Before
    public void setUp() {
        eventualResponseEvent = CompletableFuture.supplyAsync(() -> HttpStatus.ACCEPTED);
    }

    @Test
    public void shouldCreateRawDataStatistics() throws Exception {
        final byte[] byteArray = new byte[0];
        ByteArrayResource resource = new ByteArrayResource(byteArray);
        Mockito.when(mockStatisticsService.createRawDataStatistics(resource)).
                thenReturn(eventualResponseEvent);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(StatisticsRoutes.EVENT).content(byteArray);
        mockMvc.perform(requestBuilder).andExpect(status().isAccepted());
        Mockito.verify(mockStatisticsService, times(1)).createRawDataStatistics(resource);
    }

    @Test
    public void shouldGetStatistics() throws Exception {
        final byte[] byteArray = new byte[0];
        ByteArrayResource resource = new ByteArrayResource(byteArray);
        Mockito.when(mockStatisticsService.createRawDataStatistics(resource)).
                thenReturn(eventualResponseEvent);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(StatisticsRoutes.STATS);
        mockMvc.perform(requestBuilder).andExpect(status().isAccepted());
        Mockito.verify(mockStatisticsService, times(1)).createRawDataStatistics(resource);
    }
}