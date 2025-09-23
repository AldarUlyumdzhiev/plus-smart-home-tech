package ru.yandex.practicum.commerce.dto.warehouse;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * Представление адреса в системе
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AddressDto {
    String country;
    String city;
    String street;
    String house;
    String flat;
}
