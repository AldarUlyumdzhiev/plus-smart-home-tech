package ru.yandex.practicum.kafka.telemetry.collector.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
public class HubEventDto {
    @JsonAlias({"hubId","hub_id"})
    String hubId;

    @JsonAlias({"timestamp","ts","time"})
    Instant timestamp;

    @JsonAlias({"kind","eventType","type"})
    HubEventKind kind;

    // DEVICE_*
    @JsonAlias({"deviceId","device_id","id"})
    String deviceId;

    @JsonAlias({"deviceType","device_type"})
    DeviceTypeDto deviceType;

    // SCENARIO_*
    @JsonAlias({"scenarioName","name"})
    String scenarioName;

    List<ScenarioConditionDto> conditions;
    List<DeviceActionDto> actions;
}
