package ru.yandex.practicum.kafka.telemetry.collector.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ConditionTypeDto {
    MOTION, LUMINOSITY, SWITCH, TEMPERATURE, CO2LEVEL, HUMIDITY;

    @JsonCreator
    public static ConditionTypeDto from(String raw) {
        return raw == null ? null : ConditionTypeDto.valueOf(raw.trim().toUpperCase());
    }
}
