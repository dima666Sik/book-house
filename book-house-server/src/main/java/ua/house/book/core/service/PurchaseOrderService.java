package ua.house.book.core.service;

import org.springframework.security.core.Authentication;
import ua.house.book.core.domain.dto.request.OrderDTO;
import ua.house.book.core.domain.dto.request.OrdersDTO;

import java.util.List;

public interface PurchaseOrderService {
    void createListOrder(Authentication authentication, OrdersDTO ordersDTO);
}
