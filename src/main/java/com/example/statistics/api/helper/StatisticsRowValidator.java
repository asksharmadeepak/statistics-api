package com.example.statistics.api.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StatisticsRowValidator {

    @Value("${data.start.range}")
    private Integer startRange;

    @Value("${data.end.range}")
    private Integer endRange;

    @Value("${data.fractional.part}")
    private Integer fractionalPart;

    public boolean validateInputRowData(String inputRealNumber, Integer inputInteger) {
        if (inputInteger > startRange && inputInteger < endRange || validateInputX(inputRealNumber) < fractionalPart){
            return true;
        }else return false;
    }

    private int validateInputX(String inputRealNumber){
        String text = Double.toString(Math.abs(Double.valueOf(inputRealNumber)));
        int integerPlaces = text.indexOf('.');
        int decimalPlaces = text.length() - integerPlaces - 1;
        return decimalPlaces;
    }

}
