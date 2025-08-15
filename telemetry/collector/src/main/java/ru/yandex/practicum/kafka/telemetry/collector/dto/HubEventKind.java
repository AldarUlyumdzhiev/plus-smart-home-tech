package ru.yandex.practicum.kafka.telemetry.collector.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum HubEventKind {
    DEVICE_ADDED, DEVICE_REMOVED, SCENARIO_ADDED, SCENARIO_REMOVED;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static HubEventKind from(String raw) {
        return raw == null ? null : HubEventKind.valueOf(raw.trim().toUpperCase());
    }
}