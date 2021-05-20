package com.example.statistics.api.helper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class StatisticsRowValidatorTest {

    @InjectMocks
    StatisticsRowValidator statisticsRowValidator = new StatisticsRowValidator();

    @Test
    public void shouldReturnTrueForValidStatisticsRow(){
        boolean isRawDataInputValid = statisticsRowValidator
                .validateInputRowData("0.0442672968", 1282509067);
        Assert.assertEquals(isRawDataInputValid, true);
    }

    @Test
    public void shouldReturnFalseForValidStatisticsRow(){
        boolean isRawDataInputValid = statisticsRowValidator
                .validateInputRowData("0.0442672968", 1282509067);
        Assert.assertEquals(isRawDataInputValid, true);
    }
}