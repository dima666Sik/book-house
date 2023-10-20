package ua.house.book.auth.service;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.house.book.auth.cofig.AuthConfig;
import ua.house.book.auth.cofig.HibernateConfig;
import ua.house.book.auth.domain.entity.Account;
import ua.house.book.auth.domain.entity.Admin;
import ua.house.book.auth.domain.entity.User;
import ua.house.book.auth.repository.AccountRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {
    @InjectMocks
    private AuthServiceImpl authService;
    @Mock
    private AccountRepository accountRepository;
    private static Account userAccount;
    private static Account adminAccount;

    @BeforeAll
    public static void init() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(AuthConfig.class, HibernateConfig.class);

        userAccount = annotationConfigApplicationContext.getBean("userAccount", Account.class);
        adminAccount = annotationConfigApplicationContext.getBean("adminAccount", Account.class);
    }

    @Test
    void registrationUserShouldBeSuccessful() {
        Mockito.doNothing().when(accountRepository).createAccount(Mockito.any());
        authService.registration(userAccount);
        Mockito.verify(accountRepository, Mockito.times(1)).createAccount(userAccount);
    }

    @Test
    void authorizationUserShouldReturnAccount() {
        Mockito.when(accountRepository.
                        findAccountByEmailAndPassword(userAccount.getEmail(),
                                userAccount.getPassword(), User.class))
                .thenReturn(Optional.ofNullable(userAccount));
        Account resFindAcc = authService.authorization(userAccount.getEmail(), userAccount.getPassword(), User.class).orElseThrow(() -> new RuntimeException("Not found User"));
        Assertions.assertEquals(userAccount, resFindAcc);
    }

    @Test
    void registrationAdminShouldBeSuccessful() {
        Mockito.doNothing().when(accountRepository).createAccount(Mockito.any());
        authService.registration(adminAccount);
        Mockito.verify(accountRepository, Mockito.times(1)).createAccount(adminAccount);
    }

    @Test
    void authorizationAdminShouldReturnAccount() {
        Mockito.when(accountRepository.
                        findAccountByEmailAndPassword(adminAccount.getEmail(),
                                adminAccount.getPassword(), Admin.class))
                .thenReturn(Optional.ofNullable(adminAccount));
        Account resFindAcc = authService.authorization(adminAccount.getEmail(), adminAccount.getPassword(), Admin.class).orElseThrow(() -> new RuntimeException("Not found Admin"));
        Assertions.assertEquals(adminAccount, resFindAcc);
    }
}