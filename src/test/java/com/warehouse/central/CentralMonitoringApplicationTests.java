package com.warehouse.central;

import com.warehouse.central.listener.SensorEventListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SpringBootTest
@EmbeddedKafka(topics = {"${spring.kafka.consumer.topic:sensor-topic}"}, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
@ExtendWith(MockitoExtension.class)
class CentralMonitoringApplicationTests {

    @Autowired
    KafkaTemplate kafkaTemplate;

    @Spy
    SensorEventListener sensorEventListener;

    @BeforeEach
    void beforeEach() throws ExecutionException, InterruptedException {
        CompletableFuture.runAsync(() -> {
            kafkaTemplate.send("sensor-topic", "test");
        }).get();
    }

    @Test
    void contextLoads() throws InterruptedException {
        // Thread.sleep(10000);
    }

}
