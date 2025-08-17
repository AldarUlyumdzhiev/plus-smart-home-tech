package ru.yandex.practicum.kafka.telemetry.collector.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ConditionOperationDto {
    EQUALS, GREATER_THAN, LOWER_THAN;

    @JsonCreator
    public static ConditionOperationDto from(String raw) {
        return raw == null ? null : ConditionOperationDto.valueOf(raw.trim().toUpperCase());
    }
}