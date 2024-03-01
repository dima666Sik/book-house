package ua.house.book.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.house.book.core.dao.ProductDAO;
import ua.house.book.core.domain.dto.request.MoneyDTO;
import ua.house.book.core.domain.dto.request.ProductDTO;
import ua.house.book.core.domain.entity.Money;
import ua.house.book.core.domain.entity.Product;
import ua.house.book.core.exception.ProductNotFoundException;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final ProductDAO productDAO;

    private Product initProduct(ProductDTO productDTORequest) {
        var money = Money.builder()
                .amount(productDTORequest.getMoneyDTO().getAmount())
                .currency(productDTORequest.getMoneyDTO().getCurrency())
                .build();
        return Product.builder()
                .productName(productDTORequest.getProductName())
                .availableCountProducts(productDTORequest.getAvailableCountProducts())
                .money(money)
                .build();
    }

    @Transactional
    @Override
    public void createProduct(ProductDTO productDTORequest) {
        var product = initProduct(productDTORequest);
        productDAO.saveProduct(product);
    }
    @Transactional
    @Override
    public void updateProduct(ProductDTO productDTORequest) {
        var product = initProduct(productDTORequest);
        productDAO.updateProduct(product);
    }
    @Transactional
    @Override
    public void deleteProduct(ProductDTO productDTORequest) {
        productDAO.deleteProduct(productDTORequest.getId());
    }

    @Override
    public ProductDTO readProduct(ProductDTO productDTORequest) {
        var product = productDAO.getProduct(productDTORequest.getId())
                .orElseThrow(() -> new ProductNotFoundException("Not found product with this id: "
                        + productDTORequest.getId()));
        return ProductDTO
                .builder()
                .productName(product.getProductName())
                .availableCountProducts(product.getAvailableCountProducts())
                .moneyDTO(MoneyDTO.builder()
                        .amount(product.getMoney().getAmount())
                        .currency(product.getMoney().getCurrency())
                        .build())
                .build();
    }
}
