package ru.yandex.practicum.kafka.telemetry.collector.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
public class ScenarioConditionDto {
    @JsonAlias({"sensorId","sensor_id"})
    String sensorId;
    ConditionTypeDto type;
    ConditionOperationDto operation;
    Object value;
}