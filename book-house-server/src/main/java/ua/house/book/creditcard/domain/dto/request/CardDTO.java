package ua.house.book.creditcard.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class CardDTO {
    @JsonProperty("number_card")
    private String numberCard;
    @JsonProperty("card_end_data_month")
    private Short cardEndDataMonth;
    @JsonProperty("card_end_data_year")
    private Short cardEndDataYear;
    private String cvc2;
    private MoneyCardDTO moneyCard;
}
