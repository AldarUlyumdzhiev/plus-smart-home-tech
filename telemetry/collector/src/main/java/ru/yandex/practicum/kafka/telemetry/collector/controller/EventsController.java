package ru.yandex.practicum.kafka.telemetry.collector.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.kafka.telemetry.collector.dto.*;
import ru.yandex.practicum.kafka.telemetry.collector.mapper.TelemetryMapper;
import ru.yandex.practicum.kafka.telemetry.collector.service.TelemetryProducer;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventsController {

    private final TelemetryProducer producer;
    private final TelemetryMapper mapper;

    @PostMapping("/sensors")
    public ResponseEntity<Void> collectSensor(@RequestBody SensorEventDto dto) throws Exception {
        producer.sendSensor(mapper.toAvro(dto));
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/hubs")
    public ResponseEntity<Void> collectHub(@RequestBody HubEventDto dto) throws Exception {
        producer.sendHub(mapper.toAvro(dto));
        return ResponseEntity.accepted().build();
    }
}
