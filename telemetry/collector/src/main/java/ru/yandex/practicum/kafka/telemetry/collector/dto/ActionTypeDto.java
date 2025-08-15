package ru.yandex.practicum.kafka.telemetry.collector.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ActionTypeDto {
    ACTIVATE, DEACTIVATE, INVERSE, SET_VALUE;

    @JsonCreator
    public static ActionTypeDto from(String raw) {
        return raw == null ? null : ActionTypeDto.valueOf(raw.trim().toUpperCase());
    }
}