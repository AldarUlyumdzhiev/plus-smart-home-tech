package ru.yandex.practicum.kafka.telemetry.collector.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults
public class DeviceActionDto {
    @JsonAlias({"sensorId","sensor_id"})
    String sensorId;
    ActionTypeDto type;
    Integer value;
}