package ua.house.book.core.dao;

import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestHibernateConfig.class, TestAuthConfig.class, TestCoreBeansConfig.class})
@Sql(value = {"classpath:drop-test-core-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ProductSortDAOImplTest {
    @Inject
    private ProductSortDAO productSortDAO;
    @Inject
    private List<Product> productList;

    @Inject
    private ProductDAO productDAO;

    @BeforeEach
    public void init(){
        productDAO.saveAllProducts(productList);
    }

    @Test
    void getAllProductsByAscendingOrder() {
        productList.sort(Comparator.comparing(Product::getProductName));
        Assertions.assertIterableEquals(productList, productSortDAO.getAllProductsByAscendingOrder());
    }

    @Test
    void getAllProductsByDescendingOrder() {
        productList.sort((e1, e2) -> e2.getProductName().compareTo(e1.getProductName()));
        Assertions.assertIterableEquals(productList, productSortDAO.getAllProductsByDescendingOrder());
    }

}