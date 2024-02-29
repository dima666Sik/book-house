package ua.house.book.core.service;

import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.house.book.auth.config.TestAuthConfig;
import ua.house.book.auth.config.TestCoreBeansConfig;
import ua.house.book.auth.config.TestHibernateConfig;
import ua.house.book.auth.dao.AccountDAO;
import ua.house.book.auth.domain.entity.Account;
import ua.house.book.auth.exception.UserNotFoundException;
import ua.house.book.core.domain.Currency;
import ua.house.book.core.domain.dto.request.MoneyDTO;
import ua.house.book.core.domain.dto.request.OrderDTO;
import ua.house.book.core.domain.dto.request.OrdersDTO;
import ua.house.book.core.domain.dto.request.PurchaseDTO;
import ua.house.book.core.domain.entity.Product;
import ua.house.book.core.domain.entity.Purchase;
import ua.house.book.creditcard.dao.CardDAO;
import ua.house.book.creditcard.dao.CardsDAOImplTest;
import ua.house.book.creditcard.domain.entity.Cards;
import ua.house.book.creditcard.exception.CardNotFoundException;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestHibernateConfig.class, TestAuthConfig.class, TestCoreBeansConfig.class})
class PurchaseOrderServiceImplTest {
    @Inject
    private PurchaseOrderService orderService;
    @Inject
    private Account userAccount;
    @Inject
    private Cards cards;
    @Inject
    private CardDAO cardDAO;

    @Test
    void createListOrder() {
        Authentication authentication = mock(Authentication.class);

        when(authentication.getName()).thenReturn(userAccount.getEmail());

        OrdersDTO ordersDTO = createMockOrdersDTO();

        try {
            orderService.createListOrder(authentication, ordersDTO);
        } catch (CardNotFoundException cardNotFoundException) {
            cardDAO.saveCard(cards);
            orderService.createListOrder(authentication, ordersDTO);
        }
    }

    private OrdersDTO createMockOrdersDTO() {
        return OrdersDTO.builder()
                .orderDTOList(List.of(OrderDTO.builder()
                                .orderDate("11/11/2002")
                                .orderTime("20:54:59")
                                .purchaseList(List.of(PurchaseDTO.builder()
                                                .countPurchases(1)
                                                .purchaseName("Pizza")
                                                .moneyDTO(MoneyDTO.builder()
                                                        .amount(120)
                                                        .currency(Currency.UAH)
                                                        .build())
                                                .build(),
                                        PurchaseDTO.builder()
                                                .countPurchases(1)
                                                .purchaseName("Burger")
                                                .moneyDTO(MoneyDTO.builder()
                                                        .amount(120)
                                                        .currency(Currency.UAH)
                                                        .build())
                                                .build()))
                                .build(),
                        OrderDTO.builder()
                                .orderDate("14/12/2001")
                                .orderTime("08:51:49")
                                .purchaseList(List.of(PurchaseDTO.builder()
                                                .countPurchases(1)
                                                .purchaseName("Pizza")
                                                .moneyDTO(MoneyDTO.builder()
                                                        .amount(120)
                                                        .currency(Currency.UAH)
                                                        .build())
                                                .build(),
                                        PurchaseDTO.builder()
                                                .countPurchases(1)
                                                .purchaseName("Burger")
                                                .moneyDTO(MoneyDTO.builder()
                                                        .amount(120)
                                                        .currency(Currency.UAH)
                                                        .build())
                                                .build()))
                                .build()))
                .build();
    }
}