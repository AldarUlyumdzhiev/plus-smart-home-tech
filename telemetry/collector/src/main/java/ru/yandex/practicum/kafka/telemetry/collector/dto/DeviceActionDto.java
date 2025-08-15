package ru.yandex.practicum.kafka.telemetry.collector.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class DeviceActionDto {
    @JsonAlias({"sensorId","sensor_id"})
    private String sensorId;
    private ActionTypeDto type;
    private Integer value;
}