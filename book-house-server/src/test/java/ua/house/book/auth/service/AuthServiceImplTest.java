package ua.house.book.auth.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.house.book.auth.config.AuthConfig;
import ua.house.book.auth.config.HibernateConfig;
import ua.house.book.auth.config.TestAuthConfig;
import ua.house.book.auth.domain.entity.Account;
import ua.house.book.auth.domain.entity.Admin;
import ua.house.book.auth.domain.entity.User;
import ua.house.book.auth.dao.AccountDAO;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateConfig.class, TestAuthConfig.class})
public class AuthServiceImplTest {
    @InjectMocks
    private AuthServiceImpl authService;
    @Mock
    private AccountDAO accountDAO;
    @Autowired
    private Account userAccount;
    @Autowired
    private Account adminAccount;

    @Test
    @Order(1)
    void registrationUserShouldBeSuccessful() {
        Mockito.doNothing().when(accountDAO).createAccount(Mockito.any());
        authService.registration(userAccount);
        Mockito.verify(accountDAO, Mockito.times(1)).createAccount(userAccount);
    }

    @Test
    @Order(2)
    void authorizationUserShouldReturnAccount() {
        Mockito.when(accountDAO.
                        findAccountByEmailAndPassword(userAccount.getEmail(),
                                userAccount.getPassword(), User.class))
                .thenReturn(Optional.ofNullable(userAccount));
        Account resFindAcc = authService.authorization(userAccount.getEmail(), userAccount.getPassword(), User.class).orElseThrow(() -> new RuntimeException("Not found User"));
        Assertions.assertEquals(userAccount, resFindAcc);
    }

    @Test
    @Order(3)
    void registrationAdminShouldBeSuccessful() {
        Mockito.doNothing().when(accountDAO).createAccount(Mockito.any());
        authService.registration(adminAccount);
        Mockito.verify(accountDAO, Mockito.times(1)).createAccount(adminAccount);
    }

    @Test
    @Order(4)
    void authorizationAdminShouldReturnAccount() {
        Mockito.when(accountDAO.
                        findAccountByEmailAndPassword(adminAccount.getEmail(),
                                adminAccount.getPassword(), Admin.class))
                .thenReturn(Optional.ofNullable(adminAccount));
        Account resFindAcc = authService.authorization(adminAccount.getEmail(), adminAccount.getPassword(), Admin.class).orElseThrow(() -> new RuntimeException("Not found Admin"));
        Assertions.assertEquals(adminAccount, resFindAcc);
    }
}