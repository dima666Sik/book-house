package ua.house.book.core.service;

import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
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
import ua.house.book.core.dao.ProductDAO;
import ua.house.book.core.dao.PurchaseOrderDAO;
import ua.house.book.core.domain.Currency;
import ua.house.book.core.domain.dto.request.MoneyDTO;
import ua.house.book.core.domain.dto.request.OrderDTO;
import ua.house.book.core.domain.dto.request.OrdersDTO;
import ua.house.book.core.domain.dto.request.PurchaseDTO;
import ua.house.book.core.domain.entity.Order;
import ua.house.book.core.domain.entity.Product;
import ua.house.book.creditcard.dao.CardDAO;
import ua.house.book.creditcard.domain.entity.Card;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestHibernateConfig.class, TestAuthConfig.class, TestCoreBeansConfig.class})
class PurchaseOrderServiceImplTest {
    @InjectMocks
    private PurchaseOrderServiceImpl orderService;
    @Mock
    private AccountDAO accountDAO;
    @Mock
    private PurchaseOrderDAO purchaseOrderDAO;
    @Mock
    private ProductDAO productDAO;
    @Inject
    private Account userAccount;
    @Inject
    private Card card;
    @Mock
    private CardDAO cardDAO;
    @Inject
    private List<Product> productList;

    @Inject
    private List<Order> orderList;

    @Test
    void createListOrder() {
        Assertions.assertDoesNotThrow(() -> {
            Authentication authentication = mock(Authentication.class);
            when(authentication.getName())
                    .thenReturn(userAccount.getEmail());

            when(productDAO.getAllProducts())
                    .thenReturn(productList);

            when(accountDAO.findAccountByEmail(authentication.getName()))
                    .thenReturn(Optional.of(userAccount));

            when(cardDAO.getCard(anyLong()))
                    .thenReturn(Optional.of(card));

            OrdersDTO ordersDTO = createMockOrdersDTO();

            orderService.createListOrder(authentication, ordersDTO);
        });
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