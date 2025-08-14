package ru.yandex.practicum.kafka.telemetry.collector.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import java.time.Instant;

@Data
public class SensorEventDto {
    private String id;
    private String hubId;
    private Instant timestamp;

    @JsonAlias({"eventType", "type"})
    private SensorPayloadType type;

    // LIGHT
    private Integer linkQuality;
    private Integer luminosity;
    // MOTION
    private Boolean motion;
    private Integer voltage;
    // SWITCH
    private Boolean state;
    // TEMPERATURE
    private Integer temperatureC;
    private Integer temperatureF;
    // CLIMATE
    private Integer humidity;
    private Integer co2Level;
}