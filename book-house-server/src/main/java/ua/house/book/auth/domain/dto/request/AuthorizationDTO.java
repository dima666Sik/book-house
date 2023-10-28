package ua.house.book.auth.domain.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorizationDTO {
    private String email;
    private String password;
}
