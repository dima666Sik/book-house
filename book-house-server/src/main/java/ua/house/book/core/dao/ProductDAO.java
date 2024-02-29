package ua.house.book.core.dao;

import ua.house.book.core.domain.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDAO {
    Long saveProduct(final Product product);
    Optional<Product> getProduct(Long idProduct);
    void updateProduct(final Product product);
    void deleteProduct(Long idProduct);
    List<Product> getAllProducts();
    void saveAllProducts(List<Product> productList);
}
