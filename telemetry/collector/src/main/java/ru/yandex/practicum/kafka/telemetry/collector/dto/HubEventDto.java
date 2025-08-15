package ru.yandex.practicum.kafka.telemetry.collector.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class HubEventDto {
    private String hubId;
    private Instant timestamp;

    @JsonAlias({"kind","eventType"})
    private HubEventKind kind;

    // для DEVICE_
    @JsonAlias({"deviceId","id"})
    private String deviceId;
    @JsonAlias({"deviceType","type"})
    private DeviceTypeDto deviceType;

    // для SCENARIO_
    @JsonAlias({"scenarioName","name"})
    private String scenarioName;
    private List<ScenarioConditionDto> conditions;
    private List<DeviceActionDto> actions;
}