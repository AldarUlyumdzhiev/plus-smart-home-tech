package ru.yandex.practicum.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.entities.embedded.ScenarioActionId;

@Entity
@Table(name = "scenario_actions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
public class ScenarioAction {

    @EmbeddedId
    ScenarioActionId id;

    @ManyToOne
    @MapsId("scenarioId") // связываем с Scenario через составной ключ
    @JoinColumn(name = "scenario_id")
    Scenario scenario;

    @ManyToOne
    @MapsId("sensorId") // связываем с Sensor через составной ключ
    @JoinColumn(name = "sensor_id")
    Sensor sensor;

    @ManyToOne
    @MapsId("actionId") // связываем с Action через составной ключ
    @JoinColumn(name = "action_id")
    Action action;
}