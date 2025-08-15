package ru.yandex.practicum.kafka.telemetry.collector.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SensorPayloadType {
    LIGHT, MOTION, CLIMATE, SWITCH, TEMPERATURE;

    @JsonCreator
    public static SensorPayloadType from(String raw) {
        if (raw == null) return null;
        return switch (raw) {
            case "LIGHT_SENSOR_EVENT", "LIGHT" -> LIGHT;
            case "MOTION_SENSOR_EVENT", "MOTION" -> MOTION;
            case "CLIMATE_SENSOR_EVENT", "CLIMATE" -> CLIMATE;
            case "SWITCH_SENSOR_EVENT", "SWITCH" -> SWITCH;
            case "TEMPERATURE_SENSOR_EVENT", "TEMPERATURE" -> TEMPERATURE;
            default -> throw new IllegalArgumentException("Unknown sensor eventType: " + raw);
        };
    }
}