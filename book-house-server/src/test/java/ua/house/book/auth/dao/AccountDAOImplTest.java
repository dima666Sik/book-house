package ua.house.book.auth.dao;

import jakarta.inject.Inject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.house.book.auth.config.TestAuthBeansConfig;
import ua.house.book.auth.config.TestHibernateConfig;
import ua.house.book.auth.config.TestAuthConfig;
import ua.house.book.auth.domain.entity.Account;
import ua.house.book.auth.exception.UserNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestHibernateConfig.class, TestAuthConfig.class, TestAuthBeansConfig.class})
@Sql(value = {"/src/test/resources/drop-test-users-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class AccountDAOImplTest {
    @Inject
    private AccountDAO accountDAO;
    @Inject
    private Account userAccount;
    @Inject
    private Account adminAccount;

    @Test
    @Order(1)
    void createAdminShouldReturnTrue() {
        accountDAO.saveAccount(adminAccount);
        Account foundAccount = accountDAO.findAdminAccountByEmailAndPassword(adminAccount.getEmail(), adminAccount.getPassword())
                .orElseThrow(() -> new UserNotFoundException("Not found account with this args: "
                        + adminAccount.getEmail() + " " + adminAccount.getPassword()));
        Assertions.assertEquals(adminAccount, foundAccount);
    }
    @Test
    @Order(2)
    void createUserShouldReturnTrue() {
        accountDAO.saveAccount(userAccount);
        Account foundAccount = accountDAO.findUserAccountByEmailAndPassword(userAccount.getEmail(), userAccount.getPassword())
                .orElseThrow(() -> new UserNotFoundException("Not found account with this args: "
                        + userAccount.getEmail() + " " + userAccount.getPassword()));
        System.out.println(foundAccount);
        Assertions.assertEquals(userAccount, foundAccount);
    }
}