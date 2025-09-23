package ru.yandex.practicum.commerce.dto.warehouse;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Общие сведения о зарезервированных товарах по корзине
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Setter
@Getter
public class BookedProductsDto {
    @NotNull
    Double deliveryWeight;

    @NotNull
    Double deliveryVolume;

    @NotNull
    Boolean fragile;
}
