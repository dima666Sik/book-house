package ua.house.book.auth.dao;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.house.book.auth.config.TestHibernateConfig;
import ua.house.book.auth.config.TestAuthConfig;
import ua.house.book.auth.domain.entity.Account;
import ua.house.book.auth.domain.entity.Admin;
import ua.house.book.auth.domain.entity.User;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestHibernateConfig.class, TestAuthConfig.class})
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