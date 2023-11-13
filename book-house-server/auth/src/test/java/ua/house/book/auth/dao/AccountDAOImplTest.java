package ua.house.book.auth.dao;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.house.book.auth.config.TestHibernateConfig;
import ua.house.book.auth.config.TestAuthConfig;
import ua.house.book.auth.domain.entity.Account;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestHibernateConfig.class, TestAuthConfig.class})
@Sql(value = {"/drop-test-users-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class AccountDAOImplTest {
    @Autowired
    private AccountDAO accountDAO;
    @Autowired
    private Account userAccount;
    @Autowired
    private Account adminAccount;

    @Test
    @Order(1)
    void createUserShouldReturnTrue() {
        accountDAO.saveAccount(userAccount);
        Account foundAccount = accountDAO.findUserAccountByEmailAndPassword(userAccount.getEmail(), userAccount.getPassword())
                .orElseThrow(() -> new IllegalArgumentException("Not found account with this args: "
                        + userAccount.getEmail() + " " + userAccount.getPassword()));
        System.out.println(foundAccount);
        Assertions.assertEquals(userAccount, foundAccount);
    }

    @Test
    @Order(2)
    void createAdminShouldReturnTrue() {
        accountDAO.saveAccount(adminAccount);
        Account foundAccount = accountDAO.findAdminAccountByEmailAndPassword(adminAccount.getEmail(), adminAccount.getPassword())
                .orElseThrow(() -> new IllegalArgumentException("Not found account with this args: "
                        + adminAccount.getEmail() + " " + adminAccount.getPassword()));
        Assertions.assertEquals(adminAccount, foundAccount);
    }
}