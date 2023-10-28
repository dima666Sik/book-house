package ua.house.book.auth.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.house.book.auth.config.TestAuthConfig;
import ua.house.book.auth.config.TestHibernateConfig;
import ua.house.book.auth.domain.dto.request.AuthorizationDTO;
import ua.house.book.auth.domain.dto.request.RegistrationDTO;
import ua.house.book.auth.domain.entity.Account;
import ua.house.book.auth.service.AuthService;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestHibernateConfig.class, TestAuthConfig.class})
class AuthControllerTest {
    @InjectMocks
    private AuthController authController;
    @Mock
    private AuthService authService;

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
    void testOrdinalRegistration() {
        Mockito.doNothing().when(authService).ordinalRegistration(Mockito.any());
        authController.ordinalRegistration(userAccountRegistrationDtoRequest);
        Mockito.verify(authService, Mockito.times(1)).ordinalRegistration(userAccountRegistrationDtoRequest);
    }

    @Test
    void testAdminRegistration() {
        Mockito.doNothing().when(authService).adminRegistration(Mockito.any());
        authController.adminRegistration(adminAccountRegistrationDtoRequest);
        Mockito.verify(authService, Mockito.times(1)).adminRegistration(adminAccountRegistrationDtoRequest);
    }

    @Test
    void testOrdinalAuthorization() {
        Mockito.when(authService.authorization(userAuthorizationDtoRequest))
                .thenReturn(userAccount);

        // Call the controller method
        ResponseEntity<String> responseEntity = authController.authorization(userAuthorizationDtoRequest);

        // Verify the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("User authorization " + userAccount.getEmail() + " is successful", responseEntity.getBody());
    }

    @Test
    void testAdminAuthorization() {
        Mockito.when(authService.authorization(adminAuthorizationDtoRequest))
                .thenReturn(adminAccount);

        // Call the controller method
        ResponseEntity<String> responseEntity = authController.authorization(adminAuthorizationDtoRequest);

        // Verify the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("User authorization " + adminAccount.getEmail() + " is successful", responseEntity.getBody());
    }
}