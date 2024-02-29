package ua.house.book.auth.domain.dto.response;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class AuthResponseDTO {
    private String token;
}
