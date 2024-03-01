package ua.house.book.core.dao;

import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.house.book.auth.config.TestAuthConfig;
import ua.house.book.auth.config.TestCoreBeansConfig;
import ua.house.book.auth.config.TestHibernateConfig;
import ua.house.book.core.domain.entity.Product;
import ua.house.book.core.exception.ProductNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestHibernateConfig.class, TestAuthConfig.class, TestCoreBeansConfig.class})
@Sql(value = {"classpath:drop-test-core-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ProductDAOImplTest {
    @Inject
    private ProductDAO productDAO;
    @Inject
    private Product product;
    @Inject
    private List<Product> productList;

    @Test
    @Order(1)
    void saveProductShouldBeFound() {
        productDAO.saveProduct(product);
        Product foundProduct = productDAO.getProduct(product.getId())
                .orElseThrow(() -> new ProductNotFoundException("Not found product with this id: "
                        + product.getId()));
        System.out.println(foundProduct);
        Assertions.assertEquals(product, foundProduct);
    }


    @Test
    @Order(2)
    void updateProductShouldBeFound() {
        product.setProductName("Onion");
        productDAO.updateProduct(product);
        Product foundProduct = productDAO.getProduct(product.getId())
                .orElseThrow(() -> new ProductNotFoundException("Not found product with this id: "
                        + product.getId()));
        System.out.println(foundProduct);
        Assertions.assertEquals(product, foundProduct);
        product.setProductName("Pizza");
    }

    @Test
    @Order(3)
    void deleteProductShouldNotBeFound() {
        long productId = productDAO.saveProduct(product);

        productDAO.deleteProduct(productId);

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            productDAO.getProduct(product.getId())
                    .orElseThrow(() -> new ProductNotFoundException("Not found product with this id: "
                            + product.getId()));
        });

        // Assert the message in the exception
        String expectedMessage = "Not found product with this id: " + productId;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @Order(4)
    void saveAllProductsShouldBeFounds() {
        productDAO.saveAllProducts(productList);
        List<Product> foundListProducts
                = productDAO.getAllProducts();
        Assertions.assertIterableEquals(productList, foundListProducts);
    }

}