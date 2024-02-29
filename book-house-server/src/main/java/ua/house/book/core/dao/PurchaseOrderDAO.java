package ua.house.book.core.dao;

import ua.house.book.core.domain.entity.Order;

import java.util.List;

public interface PurchaseOrderDAO {
    void createListOrder(List<Order> orderList);
    List<Order> getListOrder(Long idAccount);
}
