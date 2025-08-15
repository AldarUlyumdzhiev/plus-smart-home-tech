package ru.yandex.practicum.kafka.telemetry.collector.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.time.Instant;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HubEventDto {
    private String hubId;
    private Instant timestamp;

    @JsonAlias({"kind", "eventType", "type"})
    private HubEventKind kind;

    @JsonAlias({"deviceId", "id"})
    private String deviceId;

    @JsonAlias({"deviceType"})
    private DeviceTypeDto deviceType;
}