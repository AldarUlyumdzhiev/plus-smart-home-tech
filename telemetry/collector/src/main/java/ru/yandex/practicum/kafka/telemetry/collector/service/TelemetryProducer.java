package ru.yandex.practicum.kafka.telemetry.collector.service;

import lombok.RequiredArgsConstructor;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
public class TelemetryProducer {

    private final KafkaTemplate<String, byte[]> kafka;

    @Value("${telemetry.topics.sensors:telemetry.sensors.v1}")
    private String sensorsTopic;

    @Value("${telemetry.topics.hubs:telemetry.hubs.v1}")
    private String hubsTopic;

    public void sendSensor(SensorEventAvro event) throws Exception {
        kafka.send(sensorsTopic, event.getId(), serialize(event));
    }

    public void sendHub(HubEventAvro event) throws Exception {
        kafka.send(hubsTopic, event.getHubId(), serialize(event));
    }

    private byte[] serialize(SpecificRecordBase record) throws Exception {
        try (var out = new ByteArrayOutputStream()) {
            var writer = new SpecificDatumWriter<SpecificRecordBase>(record.getSchema());
            BinaryEncoder enc = EncoderFactory.get().binaryEncoder(out, null);
            writer.write(record, enc);
            enc.flush();
            return out.toByteArray();
        }
    }
}
