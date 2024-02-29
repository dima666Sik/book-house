package ua.house.book.core.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.house.book.auth.domain.dto.request.AuthorizationDTO;
import ua.house.book.auth.domain.dto.request.RegistrationDTO;
import ua.house.book.auth.domain.entity.Account;
import ua.house.book.core.domain.dto.request.ProductDTO;
import ua.house.book.core.domain.entity.Product;
import ua.house.book.core.service.ProductSortService;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/core/sort/products")
public class ProductSortController {
    private final ProductSortService productSortService;

    @GetMapping("/asc-orders")
    public ResponseEntity<List<ProductDTO>> ascendingOrder() {
        return productSortService.getAllProductsByAscendingOrder();
    }

    @GetMapping("/desc-orders")
    public ResponseEntity<List<ProductDTO>> descendingOrder() {
        return productSortService.getAllProductsByDescendingOrder();
    }

}

