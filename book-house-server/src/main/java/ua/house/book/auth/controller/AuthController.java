package ua.house.book.auth.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.house.book.auth.domain.dto.request.AuthorizationDTO;
import ua.house.book.auth.domain.dto.request.RegistrationDTO;
import ua.house.book.auth.domain.dto.response.AuthResponseDTO;
import ua.house.book.auth.domain.entity.Account;
import ua.house.book.auth.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/users/registration")
    public ResponseEntity<AuthResponseDTO> ordinalRegistration(@Valid @RequestBody RegistrationDTO registrationDTORequest) {
        var authResponseDTO = authService.ordinalRegistration(registrationDTORequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authResponseDTO);
    }

    @PostMapping("/admins/registration")
    public ResponseEntity<AuthResponseDTO> adminRegistration(@Valid @RequestBody RegistrationDTO registrationDTORequest) {
        var authResponseDTO = authService.adminRegistration(registrationDTORequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authResponseDTO);
    }

    @PostMapping("/authorization")
    public ResponseEntity<AuthResponseDTO> authorization(@Valid @RequestBody AuthorizationDTO authorizationDTORequest) {
        var authResponseDTO = authService.authorization(authorizationDTORequest);
        return ResponseEntity
                .ok()
                .body(authResponseDTO);
    }
}

