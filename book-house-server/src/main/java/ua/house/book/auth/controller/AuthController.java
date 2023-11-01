package ua.house.book.auth.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
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

    @PostMapping("/users/registration")
    public ResponseEntity<String> ordinalRegistration(@Valid @RequestBody RegistrationDTO registrationDTORequest) {
        authService.ordinalRegistration(registrationDTORequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registration " + registrationDTORequest.getEmail() + " is successful");
    }

    @PostMapping("/admins/registration")
    public ResponseEntity<String> adminRegistration(@Valid @RequestBody RegistrationDTO registrationDTORequest) {
        authService.adminRegistration(registrationDTORequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Admin registration " + registrationDTORequest.getEmail() + " is successful");
    }

    @PostMapping("/authorization")
    public ResponseEntity<String> authorization(@Valid @RequestBody AuthorizationDTO authorizationDTORequest) {
        Account account = authService.authorization(authorizationDTORequest);
        return ResponseEntity.ok().body("User authorization " + account.getEmail() + " is successful");
    }
}
