package ua.house.book.core.service;

import org.springframework.http.ResponseEntity;
import ua.house.book.core.domain.dto.request.ProductDTO;

import java.util.List;

public interface ProductSortService {
    ResponseEntity<List<ProductDTO>> getAllProductsByAscendingOrder();

    ResponseEntity<List<ProductDTO>> getAllProductsByDescendingOrder();
}
