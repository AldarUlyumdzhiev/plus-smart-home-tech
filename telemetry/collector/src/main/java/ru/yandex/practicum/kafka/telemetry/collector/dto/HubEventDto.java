package ru.yandex.practicum.kafka.telemetry.collector.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class HubEventDto {
    @JsonAlias({"hubId","hub_id"})
    private String hubId;

    @JsonAlias({"timestamp","ts","time"})
    private Instant timestamp;

    @JsonAlias({"kind","eventType","type"})
    private HubEventKind kind;

    // DEVICE_*
    @JsonAlias({"deviceId","device_id","id"})
    private String deviceId;

    @JsonAlias({"deviceType","device_type"})
    private DeviceTypeDto deviceType;

    // SCENARIO_*
    @JsonAlias({"scenarioName","name"})
    private String scenarioName;

    private List<ScenarioConditionDto> conditions;
    private List<DeviceActionDto> actions;
}
