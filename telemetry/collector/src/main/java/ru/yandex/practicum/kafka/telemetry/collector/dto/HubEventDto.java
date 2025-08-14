package ru.yandex.practicum.kafka.telemetry.collector.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import java.time.Instant;

@Data
public class HubEventDto {
    private String hubId;
    private Instant timestamp;

    @JsonAlias({"kind", "eventType"})
    private HubEventKind kind;

    // для DEVICE_ADDED / DEVICE_REMOVED
    private String deviceId;
    private DeviceTypeDto deviceType; // для DEVICE_ADDED
}
