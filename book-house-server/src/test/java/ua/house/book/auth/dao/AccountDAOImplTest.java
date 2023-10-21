package ua.house.book.auth.dao;

import org.junit.jupiter.api.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.house.book.auth.cofig.AuthConfig;
import ua.house.book.auth.cofig.HibernateConfig;
import ua.house.book.auth.domain.entity.Account;
import ua.house.book.auth.domain.entity.Admin;
import ua.house.book.auth.domain.entity.User;

public class AccountDAOImplTest {
    private static AccountDAO accountDAO;
    private static Account userAccount;
    private static Account adminAccount;

    @BeforeAll
    public static void init() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(AuthConfig.class, HibernateConfig.class);
        accountDAO = annotationConfigApplicationContext.getBean("accountDAOImpl", AccountDAO.class);
        userAccount = annotationConfigApplicationContext.getBean("userAccount", Account.class);
        adminAccount = annotationConfigApplicationContext.getBean("adminAccount", Account.class);
    }

    @Test
    @Disabled
    @Order(1)
    void createUserShouldReturnTrue() {
        accountDAO.createAccount(userAccount);
    }

    @Test
    @Disabled
    @Order(2)
    void createAdminShouldReturnTrue() {
        accountDAO.createAccount(adminAccount);
    }

    @Test
    @Order(3)
    void findUserAccountByEmailAndPasswordShouldReturnAccount() {
        Account foundAccount = accountDAO.findAccountByEmailAndPassword(userAccount.getEmail(), userAccount.getPassword(), User.class)
                .orElseThrow(() -> new IllegalArgumentException("Not found account with this args: "
                        + userAccount.getEmail() + " " + userAccount.getPassword()));
        Assertions.assertEquals(userAccount, foundAccount);
    }

    @Test
    @Order(4)
    void findAdminAccountByEmailAndPasswordShouldReturnAccount() {
        Account foundAccount = accountDAO.findAccountByEmailAndPassword(adminAccount.getEmail(), adminAccount.getPassword(), Admin.class)
                .orElseThrow(() -> new IllegalArgumentException("Not found account with this args: "
                        + adminAccount.getEmail() + " " + adminAccount.getPassword()));
        Assertions.assertEquals(adminAccount, foundAccount);
    }
}