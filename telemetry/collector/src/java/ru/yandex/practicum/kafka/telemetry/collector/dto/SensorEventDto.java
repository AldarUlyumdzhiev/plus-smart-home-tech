package ru.yandex.practicum.kafka.telemetry.collector.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class SensorEventDto {
    private String id;
    private String hubId;
    private Instant timestamp;
    private SensorPayloadType type;

    // поля под LIGHT
    private Integer linkQuality;
    private Integer luminosity;

    // поля под MOTION
    private Boolean motion;
    private Integer voltage;

    // поля под SWITCH
    private Boolean state;

    // поля под TEMPERATURE
    private Integer temperatureC;
    private Integer temperatureF;

    // поля под CLIMATE
    private Integer humidity;
    private Integer co2Level;
}