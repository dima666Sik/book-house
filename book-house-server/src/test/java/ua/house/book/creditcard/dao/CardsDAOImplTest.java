package ua.house.book.creditcard.dao;

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
import ua.house.book.core.exception.ProductNotFoundException;
import ua.house.book.creditcard.domain.entity.Cards;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestHibernateConfig.class, TestAuthConfig.class, TestCoreBeansConfig.class})
@Sql(value = {"/src/test/resources/drop-test-credit-card-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CardsDAOImplTest {
    @Inject
    private CardDAO cardDAO;
    @Inject
    private Account userAccount;
    @Inject
    private AccountDAO accountDAO;
    @Inject
    private Cards cards;

    @Test
    public void saveCard() {
        Account account = null;
        try {
            account = accountDAO.findAccountByEmail(userAccount.getEmail())
                    .orElseThrow(() -> new UserNotFoundException("Not found user by this email: " + userAccount.getEmail()));
        } catch (UserNotFoundException e) {
            accountDAO.saveAccount(userAccount);
            account = accountDAO.findAccountByEmail(userAccount.getEmail())
                    .orElseThrow(() -> new UserNotFoundException("Not found user by this email: " + userAccount.getEmail()));
        }

        cardDAO.saveCard(cards);
        final Account finalAccount = account;
        Cards cardsFound = cardDAO.getCard(account.getId())
                .orElseThrow(() -> new ProductNotFoundException("Not found card with this user id: "
                        + finalAccount.getId()));
        System.out.println(cardsFound);
        Assertions.assertEquals(cards, cardsFound);
    }
}