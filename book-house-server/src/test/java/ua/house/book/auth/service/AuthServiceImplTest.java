package ua.house.book.auth.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.house.book.auth.config.TestHibernateConfig;
import ua.house.book.auth.config.TestAuthConfig;
import ua.house.book.auth.domain.dto.request.AuthorizationDTO;
import ua.house.book.auth.domain.dto.request.RegistrationDTO;
import ua.house.book.auth.domain.entity.Account;
import ua.house.book.auth.domain.entity.Admin;
import ua.house.book.auth.domain.entity.User;
import ua.house.book.auth.dao.AccountDAO;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestHibernateConfig.class, TestAuthConfig.class})
public class AuthServiceImplTest {
    @InjectMocks
    private AuthServiceImpl authService;
    @Mock
    private AccountDAO accountDAO;
    @Autowired
    private Account userAccount;
    @Autowired
    private Account adminAccount;
    @Autowired
    private RegistrationDTO userAccountRegistrationDtoRequest;
    @Autowired
    private RegistrationDTO adminAccountRegistrationDtoRequest;
    @Autowired
    private AuthorizationDTO userAuthorizationDtoRequest;
    @Autowired
    private AuthorizationDTO adminAuthorizationDtoRequest;

    @Test
    @Order(1)
    void registrationUserShouldBeSuccessful() {
        Mockito.doNothing().when(accountDAO).createAccount(Mockito.any());
        authService.ordinalRegistration(userAccountRegistrationDtoRequest);
        Mockito.verify(accountDAO, Mockito.times(1)).createAccount(userAccount);
    }

    @Test
    @Order(2)
    void authorizationUserShouldReturnAccount() {
        Mockito.when(accountDAO.
                        findAccountByEmailAndPassword(userAccount.getEmail(),
                                userAccount.getPassword()))
                .thenReturn(Optional.ofNullable(userAccount));
        Assertions.assertEquals(userAccount, authService.authorization(userAuthorizationDtoRequest));
    }

    @Test
    @Order(3)
    void registrationAdminShouldBeSuccessful() {
        Mockito.doNothing().when(accountDAO).createAccount(Mockito.any());
        authService.adminRegistration(adminAccountRegistrationDtoRequest);
        Mockito.verify(accountDAO, Mockito.times(1)).createAccount(adminAccount);
    }

    @Test
    @Order(4)
    void authorizationAdminShouldReturnAccount() {
        Mockito.when(accountDAO.
                        findAccountByEmailAndPassword(adminAccount.getEmail(),
                                adminAccount.getPassword()))
                .thenReturn(Optional.ofNullable(adminAccount));
        Assertions.assertEquals(adminAccount, authService.authorization(adminAuthorizationDtoRequest));
    }

}