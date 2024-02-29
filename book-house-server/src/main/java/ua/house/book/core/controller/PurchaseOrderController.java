package ua.house.book.core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.house.book.core.domain.dto.request.OrdersDTO;
import ua.house.book.core.service.PurchaseOrderService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/core/orders")
public class PurchaseOrderController {
    private final PurchaseOrderService purchaseOrderService;

    @PostMapping("/create-order")
    public ResponseEntity<String> createListOrder(Authentication authentication, @RequestBody OrdersDTO ordersDTO) {
        purchaseOrderService.createListOrder(authentication, ordersDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Order " + ordersDTO + " is successful");
    }

}

