package ua.house.book.core.dao;

import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.house.book.auth.config.TestAuthConfig;
import ua.house.book.auth.config.TestCoreBeansConfig;
import ua.house.book.auth.config.TestHibernateConfig;
import ua.house.book.auth.dao.AccountDAO;
import ua.house.book.auth.domain.entity.Account;
import ua.house.book.auth.exception.UserNotFoundException;
import ua.house.book.core.domain.entity.Order;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestHibernateConfig.class, TestAuthConfig.class, TestCoreBeansConfig.class})
class PurchaseOrderDAOImplTest {
    @Inject
    private PurchaseOrderDAO purchaseOrderDAO;

    @Inject
    private Account userAccount;

    @Inject
    private List<Order> orderList;

    @Inject
    private AccountDAO accountDAO;

    @Test
    void createListOrderShouldBeFound() {
        Account account = null;
        try {
            account = accountDAO.findAccountByEmail(userAccount.getEmail())
                    .orElseThrow(() -> new UserNotFoundException("Not found user by this email: " + userAccount.getEmail()));
        } catch (UserNotFoundException e) {
            accountDAO.saveAccount(userAccount);
            account = accountDAO.findAccountByEmail(userAccount.getEmail())
                    .orElseThrow(() -> new UserNotFoundException("Not found user by this email: " + userAccount.getEmail()));
        }

        purchaseOrderDAO.createListOrder(orderList);
        List<Order> listOrderFound = purchaseOrderDAO.getListOrder(account.getId());
        Assertions.assertIterableEquals(orderList, listOrderFound);
    }
}