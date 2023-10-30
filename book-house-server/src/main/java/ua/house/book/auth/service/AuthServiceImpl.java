package ua.house.book.auth.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.house.book.auth.domain.Role;
import ua.house.book.auth.domain.dto.request.AuthorizationDTO;
import ua.house.book.auth.domain.dto.request.RegistrationDTO;
import ua.house.book.auth.domain.entity.Account;
import ua.house.book.auth.dao.AccountDAO;
import ua.house.book.auth.domain.entity.Admin;
import ua.house.book.auth.domain.entity.User;
import ua.house.book.auth.exception.UserNotFoundException;

import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {
    private final AccountDAO accountDAO;

    @Override
    @Transactional
    public void ordinalRegistration(RegistrationDTO accountDTORequest) {
        Account user = User.builder()
                .email(accountDTORequest.getEmail())
                .password(accountDTORequest.getPassword())
                .username(accountDTORequest.getUsername())
                .roleSet(Set.of(Role.USER))
                .build();
        accountDAO.createAccount(user);
    }
    @Override
    @Transactional
    public void adminRegistration(RegistrationDTO accountDTORequest) {
        Account admin = Admin.builder()
                .email(accountDTORequest.getEmail())
                .password(accountDTORequest.getPassword())
                .username(accountDTORequest.getUsername())
                .roleSet(Set.of(Role.ADMIN))
                .build();
        accountDAO.createAccount(admin);
    }

    @Override
    public Account authorization(AuthorizationDTO authorizationDTORequest) {
        return accountDAO
                .findAccountByEmailAndPassword(authorizationDTORequest.getEmail(), authorizationDTORequest.getPassword())
                .orElseThrow(() -> new UserNotFoundException("User with email " + authorizationDTORequest.getEmail() + " not found!"));
    }
}
