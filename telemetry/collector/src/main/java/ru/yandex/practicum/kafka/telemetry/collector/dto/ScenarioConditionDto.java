package ru.yandex.practicum.kafka.telemetry.collector.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class ScenarioConditionDto {
    @JsonAlias({"sensorId","sensor_id"})
    private String sensorId;
    private ConditionTypeDto type;
    private ConditionOperationDto operation;
    private Object value;
}