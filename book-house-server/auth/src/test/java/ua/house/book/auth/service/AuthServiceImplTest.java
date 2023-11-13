package ua.house.book.auth.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.house.book.auth.config.TestHibernateConfig;
import ua.house.book.auth.config.TestAuthConfig;
import ua.house.book.auth.dao.TokenDAO;
import ua.house.book.auth.domain.dto.request.AuthorizationDTO;
import ua.house.book.auth.domain.dto.request.RegistrationDTO;
import ua.house.book.auth.domain.entity.Account;
import ua.house.book.auth.dao.AccountDAO;
import ua.house.book.auth.domain.entity.Token;
import ua.house.book.auth.service.jwt.JwtService;

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
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private TokenDAO tokenDAO;
    @Mock
    private AuthenticationManager authenticationManager;
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
        Mockito.doNothing().when(accountDAO).saveAccount(Mockito.any());
        Mockito.when(passwordEncoder.encode(Mockito.any())).thenReturn("qwerty");
        Mockito.when(jwtService.generateToken(Mockito.any())).thenReturn("token");
        authService.ordinalRegistration(userAccountRegistrationDtoRequest);
        Mockito.verify(accountDAO, Mockito.times(1)).saveAccount(userAccount);
    }

    @Test
    @Order(2)
    void authorizationUserShouldReturnAccount() {
        Mockito.when(accountDAO.
                        findAccountByEmail(userAccount.getEmail()))
                .thenReturn(Optional.ofNullable(userAccount));
        Mockito.when(jwtService.generateToken(Mockito.any())).thenReturn("token");
        Authentication mockAuthentication = Mockito.mock(Authentication.class);
        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(mockAuthentication);
        Assertions.assertEquals(userAccount, authService.authorization(userAuthorizationDtoRequest));
    }

    @Test
    @Order(3)
    void registrationAdminShouldBeSuccessful() {
        Mockito.doNothing().when(accountDAO).saveAccount(Mockito.any());
        Mockito.when(passwordEncoder.encode(Mockito.any())).thenReturn("admin");
        Mockito.when(jwtService.generateToken(Mockito.any())).thenReturn("token");
        authService.adminRegistration(adminAccountRegistrationDtoRequest);
        Mockito.verify(accountDAO, Mockito.times(1)).saveAccount(adminAccount);
    }

    @Test
    @Order(4)
    void authorizationAdminShouldReturnAccount() {
        Mockito.when(accountDAO.
                        findAccountByEmail(adminAccount.getEmail()))
                .thenReturn(Optional.ofNullable(adminAccount));
        Mockito.when(jwtService.generateToken(Mockito.any())).thenReturn("token");
        Authentication mockAuthentication = Mockito.mock(Authentication.class);
        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(mockAuthentication);
        Assertions.assertEquals(adminAccount, authService.authorization(adminAuthorizationDtoRequest));
    }

}