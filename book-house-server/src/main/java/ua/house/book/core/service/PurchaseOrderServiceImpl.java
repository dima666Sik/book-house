package ua.house.book.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.house.book.auth.dao.AccountDAO;
import ua.house.book.auth.domain.entity.Account;
import ua.house.book.auth.exception.UserNotFoundException;
import ua.house.book.core.dao.ProductDAO;
import ua.house.book.core.dao.PurchaseOrderDAO;
import ua.house.book.core.domain.dto.request.OrderDTO;
import ua.house.book.core.domain.dto.request.OrdersDTO;
import ua.house.book.core.domain.dto.request.PurchaseDTO;
import ua.house.book.core.domain.entity.Order;
import ua.house.book.core.domain.entity.Product;
import ua.house.book.core.domain.entity.Purchase;
import ua.house.book.creditcard.dao.CardDAO;
import ua.house.book.creditcard.domain.entity.Cards;
import ua.house.book.creditcard.domain.mapper.Mapper;
import ua.house.book.creditcard.exception.CardNotFoundException;
import ua.house.book.creditcard.exception.OnCardHaveNotEnoughMoney;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
    private final PurchaseOrderDAO purchaseOrderDAO;
    private final ProductDAO productDAO;
    private final AccountDAO accountDAO;
    private final CardDAO cardDAO;

    @Transactional
    @Override
    public void createListOrder(Authentication authentication, OrdersDTO ordersDTO) {
        String userEmail = authentication.getName();
        Account account = accountDAO.findAccountByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("User with email " + userEmail + " not found!"));

        List<Order> orderList = new ArrayList<>();

        ordersDTO.getOrderDTOList()
                .forEach(orderDTO -> {
                    List<Purchase> purchaseList = Mapper.purchaseDTOListIntoPurchaseList(orderDTO.getPurchaseList());
                    orderList.add(Order.builder()
                            .purchaseList(purchaseList)
                            .orderDate(orderDTO.getOrderDate())
                            .orderTime(orderDTO.getOrderTime())
                            .account(account)
                            .build());
                });

        Integer sumAllPurchases = findGeneralSumForOrders(ordersDTO);

        updateAvailableCountProduct(ordersDTO);

        Cards cards = cardDAO.getCard(account.getId())
                .orElseThrow(() -> new CardNotFoundException("Card on this id account " + account.getId() + " not found!"));
        Integer currentAmountIntoCard = cards.getMoneyCards().getMoney().getAmount();

        if (sumAllPurchases > currentAmountIntoCard)
            throw new OnCardHaveNotEnoughMoney("For creating orders u have not into card: "
                    + (sumAllPurchases - currentAmountIntoCard));

        cards.getMoneyCards().getMoney().setAmount(currentAmountIntoCard - sumAllPurchases);
        cardDAO.saveCard(cards);
        purchaseOrderDAO.createListOrder(orderList);
    }

    private Integer findGeneralSumForOrders(OrdersDTO ordersDTO) {
        return ordersDTO.getOrderDTOList()
                .stream()
                .flatMap(orderDTO -> Mapper.purchaseDTOListIntoPurchaseList(orderDTO.getPurchaseList())
                        .stream()
                        .map(purchase -> purchase.getMoney().getAmount()))
                .reduce(Integer::sum)
                .orElseThrow(() -> new RuntimeException("Cannot found general sum for orders!"));
    }

    private void updateAvailableCountProduct(OrdersDTO ordersDTO) {
        List<Product> productList = ordersDTO.getOrderDTOList()
                .stream()
                .flatMap(orderDTO -> Mapper.purchaseDTOListIntoPurchaseList(orderDTO.getPurchaseList()).stream())
                .flatMap(purchase -> productDAO.getAllProducts().stream()
                        .filter(product -> purchase.getPurchaseName().equals(product.getProductName()))
                        .peek(product ->
                                product.setAvailableCountProducts(product.getAvailableCountProducts() - purchase.getCountPurchases())
                        )
                ).toList();
        productDAO.saveAllProducts(productList);
    }
}
