package ru.yandex.practicum.kafka.telemetry.collector.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum DeviceTypeDto {
    MOTION_SENSOR,
    TEMPERATURE_SENSOR,
    LIGHT_SENSOR,
    CLIMATE_SENSOR,
    SWITCH_SENSOR;

    @JsonCreator
    public static DeviceTypeDto from(String raw) {
        if (raw == null) return null;
        return switch (raw) {
            case "MOTION_SENSOR", "MOTION" -> MOTION_SENSOR;
            case "TEMPERATURE_SENSOR", "TEMPERATURE" -> TEMPERATURE_SENSOR;
            case "LIGHT_SENSOR", "LIGHT" -> LIGHT_SENSOR;
            case "CLIMATE_SENSOR", "CLIMATE" -> CLIMATE_SENSOR;
            case "SWITCH_SENSOR", "SWITCH" -> SWITCH_SENSOR;
            default -> throw new IllegalArgumentException("Unknown deviceType: " + raw);
        };
    }
}