package ua.house.book.core.service;

import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.house.book.auth.config.TestAuthConfig;
import ua.house.book.auth.config.TestCoreBeansConfig;
import ua.house.book.auth.config.TestHibernateConfig;
import ua.house.book.core.dao.ProductDAO;
import ua.house.book.core.domain.dto.request.ProductDTO;
import ua.house.book.core.domain.entity.Product;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestHibernateConfig.class, TestAuthConfig.class, TestCoreBeansConfig.class})
class ProductServiceImplTest {
    @InjectMocks
    private ProductServiceImpl productService;
    @Mock
    private ProductDAO productDAO;
    @Inject
    private ProductDTO productDTORequest;
    @Inject
    private Product product;


    @Test
    void createProduct() {
        Mockito.when(productDAO.saveProduct(product))
                .thenReturn(product.getId());
        productService.createProduct(productDTORequest);
        Mockito.verify(productDAO, Mockito.times(1)).saveProduct(product);
    }

    @Test
    void updateProduct() {
        Mockito.doNothing().when(productDAO).updateProduct(Mockito.any());
        productService.updateProduct(productDTORequest);
        Mockito.verify(productDAO, Mockito.times(1)).updateProduct(Mockito.any());
    }

    @Test
    void deleteProduct() {
        Mockito.doNothing().when(productDAO).deleteProduct(Mockito.any());
        productService.deleteProduct(productDTORequest);
        Mockito.verify(productDAO, Mockito.times(1)).deleteProduct(Mockito.any());
    }

    @Test
    void readProduct() {
        Mockito.when(productDAO.
                        getProduct(Mockito.any()))
                .thenReturn(Optional.ofNullable(product));
        Assertions.assertEquals(productDTORequest, productService.readProduct(productDTORequest));
    }
}