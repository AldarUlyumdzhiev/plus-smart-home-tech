package ru.yandex.practicum.kafka.telemetry.collector.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.collector.dto.*;
import ru.yandex.practicum.kafka.telemetry.event.*;

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
        Object payload = switch (dto.getKind()) {
            case DEVICE_ADDED -> DeviceAddedEventAvro.newBuilder()
                    .setId(req(dto.getDeviceId(), "deviceId"))
                    .setType(DeviceTypeAvro.valueOf(req(dto.getDeviceType(), "deviceType").name()))
                    .build();
            case DEVICE_REMOVED -> DeviceRemovedEventAvro.newBuilder()
                    .setId(req(dto.getDeviceId(), "deviceId"))
                    .build();
        };

        return HubEventAvro.newBuilder()
                .setHubId(req(dto.getHubId(), "hubId"))
                .setTimestamp(req(dto.getTimestamp(), "timestamp"))
                .setPayload(payload)
                .build();
    }

    private static <T> T req(T v, String name) {
        if (v == null) throw new IllegalArgumentException(name + " is required");
        return v;
    }
}
