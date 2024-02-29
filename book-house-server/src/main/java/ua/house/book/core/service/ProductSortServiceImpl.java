package ua.house.book.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.house.book.core.dao.ProductSortDAO;
import ua.house.book.core.domain.dto.request.ProductDTO;
import ua.house.book.core.domain.entity.Product;
import ua.house.book.creditcard.domain.mapper.Mapper;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProductSortServiceImpl implements ProductSortService {
    private final ProductSortDAO productSortDAO;

    @Override
    public ResponseEntity<List<ProductDTO>> getAllProductsByAscendingOrder() {
        List<Product> productList = productSortDAO.getAllProductsByAscendingOrder();
        return ResponseEntity.ok().body(Mapper.productListIntoProductDTOList(productList));
    }

    @Override
    public ResponseEntity<List<ProductDTO>> getAllProductsByDescendingOrder() {
        List<Product> productList = productSortDAO.getAllProductsByDescendingOrder();
        return ResponseEntity.ok().body(Mapper.productListIntoProductDTOList(productList));
    }
}
