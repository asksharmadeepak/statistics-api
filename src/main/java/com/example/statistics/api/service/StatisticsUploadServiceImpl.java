package com.example.statistics.api.service;

import com.example.statistics.api.helper.StatisticsInputValidator;
import com.example.statistics.api.model.Calculation;
import com.example.statistics.api.model.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

@Service
public class StatisticsUploadServiceImpl implements StatisticsService {

    protected static final Logger LOGGER = LoggerFactory.getLogger(StatisticsUploadServiceImpl.class);

    final StatisticsInputValidator statisticsInputValidator;

    @Autowired
    public StatisticsUploadServiceImpl(StatisticsInputValidator statisticsInputValidator) {
        this.statisticsInputValidator = statisticsInputValidator;
    }

    List<Statistics> rawDataStatistics;

    @Override
    @Async
    public CompletableFuture<HttpStatus> createRawDataStatistics(Resource resource) throws IOException {
        InputStream in = resource.getInputStream();
        rawDataStatistics = Collections.synchronizedList(new ArrayList<>());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        boolean isRawDataProcessingFailed = readLinesOfRawData(reader);
        reader.close();
        return CompletableFuture.completedFuture(isRawDataProcessingFailed ? HttpStatus.PARTIAL_CONTENT : HttpStatus.ACCEPTED);
    }

    @Override
    @Async
    public CompletableFuture<String> getStatisticsPayload() {
        Timestamp currentTimestampMinus60Seconds = Timestamp.valueOf(ZonedDateTime.now().toLocalDateTime().minus(60, ChronoUnit.SECONDS));
        List<Statistics> filteredStatistics = rawDataStatistics.stream().filter(statistics -> currentTimestampMinus60Seconds.getTime() <= statistics.getTimestamp()).collect(Collectors.toList());
        DoubleSummaryStatistics collectXStats = getCollectStats(filteredStatistics, Statistics::getInputX);
        DoubleSummaryStatistics collectYStats = getCollectStats(filteredStatistics, Statistics::getInputY);
        return CompletableFuture.completedFuture(new Calculation(filteredStatistics.size(), collectXStats.getSum(),
                collectXStats.getAverage(), collectYStats.getSum(), collectYStats.getAverage()).toString());
    }

    private DoubleSummaryStatistics getCollectStats(List<Statistics> filteredStatistics, ToDoubleFunction<Statistics> mapper) {
        return filteredStatistics.stream().collect(Collectors.summarizingDouble(mapper));
    }

    private boolean readLinesOfRawData(BufferedReader reader) {
        boolean isValidationFailed = false;
        while (true) {
            try {
                String line = reader.readLine();
                if (line == null)
                    break;
                else {
                    String[] split = line.split(",");
                    if (statisticsInputValidator.validateInputRowData(split[1], Integer.parseInt(split[2]))) {
                        synchronized (rawDataStatistics) {
                            rawDataStatistics.add(new Statistics(Long.parseLong(split[0]), Double.valueOf(split[1]),
                                    Integer.parseInt(split[2])));
                        }
                    } else isValidationFailed = true;
                }
            } catch (NumberFormatException exception) {
                LOGGER.error("Row data is incorrect");
            } catch (Exception exception) {
                LOGGER.error("Row data has errors " + exception.getMessage());
            }
        }
        return isValidationFailed;
    }

}
