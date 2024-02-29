package ua.house.book.core.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ua.house.book.core.domain.Currency;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class MoneyDTO {
    @JsonProperty("amount")
    private Integer amount;
    @JsonProperty("currency")
    private Currency currency;
}
