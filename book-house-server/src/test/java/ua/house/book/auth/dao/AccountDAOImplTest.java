package ua.house.book.auth.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.house.book.auth.config.AuthConfig;
import ua.house.book.auth.config.DispatcherServetConfig;
import ua.house.book.auth.config.HibernateConfig;
import ua.house.book.auth.config.TestAuthConfig;
import ua.house.book.auth.domain.Role;
import ua.house.book.auth.domain.entity.Account;
import ua.house.book.auth.domain.entity.Admin;
import ua.house.book.auth.domain.entity.User;

import java.util.Set;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateConfig.class, TestAuthConfig.class})
public class AccountDAOImplTest {
    @Autowired
    private AccountDAO accountDAO;
    @Autowired
    private Account userAccount;
    @Autowired
    private Account adminAccount;

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
        System.out.println(foundAccount);
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