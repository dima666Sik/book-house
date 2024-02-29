package ua.house.book.auth.service;

import ua.house.book.auth.domain.dto.request.AuthorizationDTO;
import ua.house.book.auth.domain.dto.request.RegistrationDTO;
import ua.house.book.auth.domain.dto.response.AuthResponseDTO;
import ua.house.book.auth.domain.entity.Account;

import java.util.Optional;

public interface AuthService {
    AuthResponseDTO ordinalRegistration(RegistrationDTO accountDTORequest);
    AuthResponseDTO adminRegistration(RegistrationDTO accountDTORequest);
    AuthResponseDTO authorization(AuthorizationDTO authorizationDTORequest);
}
