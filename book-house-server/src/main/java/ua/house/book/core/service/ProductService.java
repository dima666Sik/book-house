package ua.house.book.core.service;

import ua.house.book.core.domain.dto.request.ProductDTO;

public interface ProductService {

    void createProduct(ProductDTO productDTORequest);

    void updateProduct(ProductDTO productDTORequest);

    void deleteProduct(ProductDTO productDTORequest);

    ProductDTO readProduct(ProductDTO productDTORequest);
}
