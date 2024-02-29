package ua.house.book.core.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.*;
import ua.house.book.core.domain.entity.Purchase;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class OrderDTO {
    List<PurchaseDTO> purchaseList;
    @JsonProperty("order_date")
    private String orderDate;
    @JsonProperty("order_time")
    private String orderTime;
}
