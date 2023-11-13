package ua.house.book.auth.service;

import ua.house.book.auth.domain.dto.request.AuthorizationDTO;
import ua.house.book.auth.domain.dto.request.RegistrationDTO;
import ua.house.book.auth.domain.entity.Account;

import java.util.Optional;

public interface AuthService {
    void ordinalRegistration(RegistrationDTO accountDTORequest);
    void adminRegistration(RegistrationDTO accountDTORequest);
    Account authorization(AuthorizationDTO authorizationDTORequest);
}
