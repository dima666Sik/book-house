package ua.house.book.core.dao;

import ua.house.book.core.domain.entity.Product;

import java.util.List;

public interface ProductSortDAO {
    List<Product> getAllProductsByAscendingOrder();

    List<Product> getAllProductsByDescendingOrder();
}
