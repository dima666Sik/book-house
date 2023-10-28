package ua.house.book.auth.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.house.book.auth.domain.dto.request.AuthorizationDTO;
import ua.house.book.auth.domain.dto.request.RegistrationDTO;
import ua.house.book.auth.domain.entity.Account;
import ua.house.book.auth.service.AuthService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;

    @PostMapping("/user/registration")
    public void ordinalRegistration(@RequestBody RegistrationDTO registrationDTORequest) {
        authService.ordinalRegistration(registrationDTORequest);
    }

    @PostMapping("/admin/registration")
    public void adminRegistration(@RequestBody RegistrationDTO registrationDTORequest) {
        authService.adminRegistration(registrationDTORequest);
    }

    @PostMapping("/authorization")
    public ResponseEntity<String> authorization(@RequestBody AuthorizationDTO authorizationDTORequest) {
        Account account = authService.authorization(authorizationDTORequest);
        return ResponseEntity.ok().body("User authorization " + account.getEmail() + " is successful");
    }
}
