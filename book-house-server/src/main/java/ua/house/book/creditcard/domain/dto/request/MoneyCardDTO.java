package ua.house.book.creditcard.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ua.house.book.core.domain.dto.request.MoneyDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class MoneyCardDTO {
    @JsonProperty("spend_limit")
    private Integer spendLimit;
    private MoneyDTO moneyDTO;
}
