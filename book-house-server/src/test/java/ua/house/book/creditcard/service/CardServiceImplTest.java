package ua.house.book.creditcard.service;

import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.house.book.auth.config.TestAuthConfig;
import ua.house.book.auth.config.TestCoreBeansConfig;
import ua.house.book.auth.config.TestHibernateConfig;
import ua.house.book.auth.dao.AccountDAO;
import ua.house.book.auth.domain.entity.Account;
import ua.house.book.core.domain.Currency;
import ua.house.book.core.domain.dto.request.MoneyDTO;
import ua.house.book.core.domain.entity.Money;
import ua.house.book.creditcard.dao.CardDAO;
import ua.house.book.creditcard.domain.dto.request.CardDTO;
import ua.house.book.creditcard.domain.dto.request.MoneyCardDTO;
import ua.house.book.creditcard.domain.entity.Card;
import ua.house.book.creditcard.domain.entity.MoneyCards;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestHibernateConfig.class, TestAuthConfig.class, TestCoreBeansConfig.class})
class CardServiceImplTest {
    @InjectMocks
    private CardServiceImpl cardService;
    @Mock
    private CardDAO cardDAO;
    @Mock
    private AccountDAO accountDAO;

    @Inject
    private Account userAccount;

    @Test
    void createCard() {
        CardDTO cardDTO = CardDTO.builder()
                .numberCard("xxxx-xxxx-xxxx-xxxx")
                .cardEndDataMonth((short)5)
                .cardEndDataYear((short)2026)
                .cvc2("859")
                .moneyCard(MoneyCardDTO.builder()
                        .spendLimit(1000)
                        .moneyDTO(MoneyDTO.builder()
                                .amount(2200)
                                .currency(Currency.UAH)
                                .build())
                        .build())
                .build();
        Mockito.doNothing().when(cardDAO).saveCard(Mockito.any());
        Authentication authentication = mock(Authentication.class);

        when(authentication.getName()).thenReturn(userAccount.getEmail());

        when(accountDAO.findAccountByEmail(userAccount.getEmail())).thenReturn(Optional.ofNullable(userAccount));

        cardService.createCard(authentication, cardDTO);

        Mockito.verify(cardDAO, Mockito.times(1)).saveCard(Card.builder()
                                                               .numberCard("xxxx-xxxx-xxxx-xxxx")
                                                               .cardEndDataMonth((short)5)
                                                               .cardEndDataYear((short)2026)
                                                               .cvc2("859")
                                                               .moneyCards(MoneyCards.builder()
                        .spendLimit(1000)
                        .money(Money.builder()
                                .id(null)
                                .amount(2200)
                                .currency(Currency.UAH)
                                .build())
                        .build())
                                                               .account(userAccount)
                                                               .build());
    }
}