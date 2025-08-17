package ru.yandex.practicum.kafka.telemetry.collector.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.collector.dto.*;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.util.List;

@Component
public class TelemetryMapper {

    public SensorEventAvro toAvro(SensorEventDto dto) {
        Object payload;
        switch (dto.getType()) {
            case LIGHT -> payload = LightSensorAvro.newBuilder()
                    .setLinkQuality(req(dto.getLinkQuality(), "linkQuality"))
                    .setLuminosity(req(dto.getLuminosity(), "luminosity"))
                    .build();

            case MOTION -> payload = MotionSensorAvro.newBuilder()
                    .setLinkQuality(req(dto.getLinkQuality(), "linkQuality"))
                    .setMotion(req(dto.getMotion(), "motion"))
                    .setVoltage(req(dto.getVoltage(), "voltage"))
                    .build();

            case SWITCH -> payload = SwitchSensorAvro.newBuilder()
                    .setState(req(dto.getState(), "state"))
                    .build();

            case TEMPERATURE -> payload = TemperatureSensorAvro.newBuilder()
                    .setId(req(dto.getId(), "id"))
                    .setHubId(req(dto.getHubId(), "hubId"))
                    .setTimestamp(req(dto.getTimestamp(), "timestamp"))
                    .setTemperatureC(req(dto.getTemperatureC(), "temperatureC"))
                    .setTemperatureF(req(dto.getTemperatureF(), "temperatureF"))
                    .build();

            case CLIMATE -> payload = ClimateSensorAvro.newBuilder()
                    .setTemperatureC(req(dto.getTemperatureC(), "temperatureC"))
                    .setHumidity(req(dto.getHumidity(), "humidity"))
                    .setCo2Level(req(dto.getCo2Level(), "co2Level"))
                    .build();

            default -> throw new IllegalArgumentException("Unsupported sensor type: " + dto.getType());
        }

        return SensorEventAvro.newBuilder()
                .setId(req(dto.getId(), "id"))
                .setHubId(req(dto.getHubId(), "hubId"))
                .setTimestamp(req(dto.getTimestamp(), "timestamp"))
                .setPayload(payload)
                .build();
    }

    public HubEventAvro toAvro(HubEventDto dto) {
        HubEventKind kind = req(dto.getKind(), "kind");

        Object payload = switch (kind) {
            case DEVICE_ADDED -> DeviceAddedEventAvro.newBuilder()
                    .setId(req(dto.getDeviceId(), "deviceId"))
                    .setType(mapDeviceType(req(dto.getDeviceType(), "deviceType")))
                    .build();
            case DEVICE_REMOVED -> DeviceRemovedEventAvro.newBuilder()
                    .setId(req(dto.getDeviceId(), "deviceId"))
                    .build();
            case SCENARIO_ADDED -> ScenarioAddedEventAvro.newBuilder()
                    .setName(req(dto.getScenarioName(), "scenarioName"))
                    .setConditions(mapConditions(dto.getConditions()))
                    .setActions(mapActions(dto.getActions()))
                    .build();
            case SCENARIO_REMOVED -> ScenarioRemovedEventAvro.newBuilder()
                    .setName(req(dto.getScenarioName(), "scenarioName"))
                    .build();
        };

        return HubEventAvro.newBuilder()
                .setHubId(req(dto.getHubId(), "hubId"))
                .setTimestamp(req(dto.getTimestamp(), "timestamp"))
                .setPayload(payload)
                .build();
    }

    private static DeviceTypeAvro mapDeviceType(DeviceTypeDto d) {
        return DeviceTypeAvro.valueOf(d.name());
    }

    private static List<ScenarioConditionAvro> mapConditions(List<ScenarioConditionDto> src) {
        if (src == null || src.isEmpty()) return List.of();
        return src.stream().map(TelemetryMapper::toAvro).toList();
    }

    private static ScenarioConditionAvro toAvro(ScenarioConditionDto c) {
        Object v = c.getValue();
        Object union;
        if (v == null) union = null;
        else if (v instanceof Boolean b) union = b;
        else if (v instanceof Number n) union = Integer.valueOf(n.intValue());
        else throw new IllegalArgumentException("conditions.value must be int or boolean");

        return ScenarioConditionAvro.newBuilder()
                .setSensorId(req(c.getSensorId(), "conditions.sensorId"))
                .setType(ConditionTypeAvro.valueOf(req(c.getType(),"conditions.type").name()))
                .setOperation(ConditionOperationAvro.valueOf(req(c.getOperation(),"conditions.operation").name()))
                .setValue(union)
                .build();
    }

    private static List<DeviceActionAvro> mapActions(List<DeviceActionDto> src) {
        if (src == null || src.isEmpty()) return List.of();
        return src.stream().map(TelemetryMapper::toAvro).toList();
    }

    private static DeviceActionAvro toAvro(DeviceActionDto a) {
        return DeviceActionAvro.newBuilder()
                .setSensorId(req(a.getSensorId(), "actions.sensorId"))
                .setType(ActionTypeAvro.valueOf(req(a.getType(),"actions.type").name()))
                .setValue(a.getValue()) // может быть null
                .build();
    }

    private static <T> T req(T v, String name) {
        if (v == null) throw new IllegalArgumentException(name + " is required");
        return v;
    }
}