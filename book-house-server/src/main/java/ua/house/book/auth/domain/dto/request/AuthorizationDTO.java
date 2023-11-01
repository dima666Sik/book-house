package ua.house.book.auth.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorizationDTO {
    @NotEmpty(message = "Please enter your Email")
    @Email(message = "Invalidate email. Please choose email right and try again...")
    private String email;

    @NotEmpty(message = "Please enter your Password.")
    @Size(min = 6, max = 8, message = "Please enter valid count symbols into your password!")
    private String password;
}
