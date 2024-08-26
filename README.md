Here's an updated `README.md` to include configurable thresholds for temperature and humidity using the `application.yml` file.

---

# Central Monitoring Service

The `central-monitoring-service` is a Spring Boot application that consumes Kafka messages to monitor temperature and humidity data. It prints alerts when the configurable thresholds for humidity and temperature are exceeded.

## Table of Contents

- [Requirements](#requirements)
- [Project Structure](#project-structure)
- [Configuration](#configuration)
- [Setup](#setup)
- [Running the Service](#running-the-service)
- [Alerting Logic](#alerting-logic)
- [Customization](#customization)

## Requirements

Before running the service, ensure you have the following installed:

- Java 11+
- Maven
- Docker (for Kafka and Zookeeper) or a Kafka setup

## Project Structure

```
.
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.central
│   │   │       ├── config
│   │   │       │   └── KafkaConsumerConfig.java
│   │   │       ├── listener
│   │   │       │   └── SensorEventListener.java
│   │   │       └── CentralMonitoringServiceApplication.java
│   │   └── resources
│   │       └── application.yml
└── pom.xml
```

- **KafkaConsumerConfig.java**: Configures the Kafka consumer properties.
- **MonitoringService.java**: Contains the business logic for monitoring humidity and temperature data based on configurable thresholds.
- **application.yml**: Configures Kafka topics, consumer settings, and sensor thresholds.

## Configuration

The `application.yml` file allows you to configure Kafka properties, topics, and sensor thresholds. Here is an example configuration:

```yaml
spring:
  application:
    name: central-monitoring-service
  sensor:
    threshold:
      humidity: 50
      temperature: 35
  kafka:
    consumer:
      bootstrap-servers: 127.0.0.1:9092
      groupid: central-service
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
      topic: sensor-topic
```

## Setup

### 1. Clone the repository:

```bash
git clone https://github.com/vinkvii/central-monitoring-service.git
cd central-monitoring-service
```

### 2. Build the project using Maven:

```bash
mvn clean install
```

### 3. Run Kafka and Zookeeper:

You can use Docker Compose to quickly spin up Kafka and Zookeeper:

```bash
docker-compose up
```

Alternatively, you can run Kafka locally by following the [Kafka Quickstart Guide](https://kafka.apache.org/quickstart).

### 4. Configure Kafka:

Ensure the `application.yml` file has the correct Kafka bootstrap server address.

## Running the Service

You can run the service using the following command:

```bash
mvn spring-boot:run
```

The service will begin consuming messages from the Kafka topic (e.g., `sensor-topic`).

## Alerting Logic

The service monitors the Kafka topic for sensor data and prints alerts based on the configured threshold values.

- **Humidity**: If the received humidity value crosses the configured threshold in `application.yml`, a message will be printed.
- **Temperature**: If the received temperature value crosses the configured threshold in `application.yml`, a message will be printed.

Example output:

```
ALERT: Humidity sensor h1 exceeded threshold with value 55
ALERT: Temperature sensor t1 exceeded threshold with value 32
```

## Customization

You can customize the following:

- **Thresholds**: Modify the threshold values in the `application.yml` file:

```yaml
  sensor:
    threshold:
      humidity: 50
      temperature: 35
```

- **Kafka Topics**: Change the topic to which the service listens in the `application.yml` file.

```yaml
spring:
  kafka:
    consumer:
      bootstrap-servers: 127.0.0.1:9092
      groupid: central-service
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
      topic: sensor-topic
```

Feel free to adjust the service further to meet your specific monitoring needs.
