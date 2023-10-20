package ua.house.book.auth.repository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.house.book.auth.cofig.AuthConfig;
import ua.house.book.auth.cofig.HibernateConfig;
import ua.house.book.auth.domain.entity.Account;
import ua.house.book.auth.domain.entity.Admin;
import ua.house.book.auth.domain.entity.User;

public class AccountRepositoryImplTest {
    private static AccountRepository accountRepository;
    private static Account userAccount;
    private static Account adminAccount;

    @BeforeAll
    public static void init() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(AuthConfig.class, HibernateConfig.class);
        accountRepository = annotationConfigApplicationContext.getBean("accountRepositoryImpl", AccountRepository.class);
        userAccount = annotationConfigApplicationContext.getBean("userAccount", Account.class);
        adminAccount = annotationConfigApplicationContext.getBean("adminAccount", Account.class);
    }

    @Test
    @Disabled
    @Order(1)
    void createUserShouldReturnTrue() {
        accountRepository.createAccount(userAccount);
    }

    @Test
    @Disabled
    @Order(2)
    void createAdminShouldReturnTrue() {
        accountRepository.createAccount(adminAccount);
    }

    @Test
    @Order(3)
    void findUserAccountByEmailAndPasswordShouldReturnAccount() {
        Account foundAccount = accountRepository.findAccountByEmailAndPassword(userAccount.getEmail(), userAccount.getPassword(), User.class)
                .orElseThrow(() -> new IllegalArgumentException("Not found account with this args: "
                        + userAccount.getEmail() + " " + userAccount.getPassword()));
        Assertions.assertEquals(userAccount, foundAccount);
    }

    @Test
    @Order(4)
    void findAdminAccountByEmailAndPasswordShouldReturnAccount() {
        Account foundAccount = accountRepository.findAccountByEmailAndPassword(adminAccount.getEmail(), adminAccount.getPassword(), Admin.class)
                .orElseThrow(() -> new IllegalArgumentException("Not found account with this args: "
                        + adminAccount.getEmail() + " " + adminAccount.getPassword()));
        Assertions.assertEquals(adminAccount, foundAccount);
    }
}