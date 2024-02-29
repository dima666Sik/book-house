package ua.house.book.core.domain.dto.request;

import lombok.*;
import ua.house.book.auth.domain.entity.Token;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class OrdersDTO {
    List<OrderDTO> orderDTOList;
}
