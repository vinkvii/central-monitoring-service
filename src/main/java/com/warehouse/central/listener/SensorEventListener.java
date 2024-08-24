package com.warehouse.central.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.warehouse.central.dto.SensorDto;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SensorEventListener {
    private final static Logger LOGGER = LoggerFactory.getLogger(SensorEventListener.class);

    @Value("${spring.application.sensor.threshold.humidity:50}")
    private int humidityThreshold;

    @Value("${spring.application.sensor.threshold.temperature:35}")
    private int temperatureThreshold;

    private final ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = "#{'${spring.kafka.consumer.topic:sensor-topic}'}")
    public void listener(String message) {
        LOGGER.debug("Received Message: {}", message);
        if (StringUtils.isNotBlank(message)) {
            filterMessageForAlert(message);
        }

    }

    private void filterMessageForAlert(String message) {
        SensorDto sensor = null;
        try {
            sensor = mapper.readValue(message, SensorDto.class);
            if ("Humidity".equalsIgnoreCase(sensor.getSensorType())) {
                if (sensor.getValue() > humidityThreshold) {
                    LOGGER.info("Humidity {},Crossed Threshold value of {}", sensor.getValue(), humidityThreshold);
                }
            } else {
                if (sensor.getValue() > temperatureThreshold) {
                    LOGGER.info("Temperature {}, Crossed Threshold value of {}", sensor.getValue(), temperatureThreshold);
                }
            }
        } catch (JsonProcessingException e) {
            System.out.println("Failed to parse event" + e);
        }
    }
}
