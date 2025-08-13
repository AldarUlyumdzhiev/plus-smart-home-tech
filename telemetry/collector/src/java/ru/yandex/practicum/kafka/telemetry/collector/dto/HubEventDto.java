package ru.yandex.practicum.kafka.telemetry.collector.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class HubEventDto {
    private String hubId;
    private Instant timestamp;
    private HubEventKind kind;

    // для DEVICE_ADDED / DEVICE_REMOVED
    private String deviceId;
    private DeviceTypeDto deviceType; // требуется только для DEVICE_ADDED
}
