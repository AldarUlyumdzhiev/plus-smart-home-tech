package ru.yandex.practicum.kafka.telemetry.collector.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults
public class ScenarioConditionDto {
    @JsonAlias({"sensorId","sensor_id"})
    String sensorId;
    ConditionTypeDto type;
    ConditionOperationDto operation;
    Object value;
}