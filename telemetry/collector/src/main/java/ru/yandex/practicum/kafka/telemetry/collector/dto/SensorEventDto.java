package ru.yandex.practicum.kafka.telemetry.collector.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
public class SensorEventDto {
    String id;
    String hubId;
    Instant timestamp;

    @JsonAlias({"eventType", "type"})
    SensorPayloadType type;

    // LIGHT
    Integer linkQuality;
    Integer luminosity;
    // MOTION
    Boolean motion;
    Integer voltage;
    // SWITCH
    Boolean state;
    // TEMPERATURE
    Integer temperatureC;
    Integer temperatureF;
    // CLIMATE
    Integer humidity;
    Integer co2Level;
}