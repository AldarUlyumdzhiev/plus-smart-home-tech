package ru.yandex.practicum.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.model.enums.ConditionOperation;
import ru.yandex.practicum.model.enums.ConditionType;

@Entity
@Table(name = "conditions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Condition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    ConditionType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation")
    ConditionOperation operation;

    @Column(name = "value_int")
    Integer valueInt;

    @Column(name = "value_bool")
    Boolean valueBool;
}