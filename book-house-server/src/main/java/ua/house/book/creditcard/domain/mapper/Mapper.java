package ua.house.book.creditcard.domain.mapper;

import ua.house.book.core.domain.dto.request.MoneyDTO;
import ua.house.book.core.domain.dto.request.ProductDTO;
import ua.house.book.core.domain.dto.request.PurchaseDTO;
import ua.house.book.core.domain.entity.Money;
import ua.house.book.core.domain.entity.Product;
import ua.house.book.core.domain.entity.Purchase;

import java.util.ArrayList;
import java.util.List;

public class Mapper {
    private Mapper() {
    }

    public static List<Purchase> purchaseDTOListIntoPurchaseList(List<PurchaseDTO> purchaseDTOList) {
        List<Purchase> purchaseList = new ArrayList<>();
        purchaseDTOList
                .forEach(purchaseDTO -> purchaseList.add(Purchase.builder()
                        .purchaseName(purchaseDTO.getPurchaseName())
                        .countPurchases(purchaseDTO.getCountPurchases())
                        .money(Money.builder()
                                .currency(purchaseDTO.getMoneyDTO().getCurrency())
                                .amount(purchaseDTO.getMoneyDTO().getAmount())
                                .build())
                        .build()));

        return purchaseList;
    }

    public static List<PurchaseDTO> purchaseListIntoPurchaseDTOList(List<Purchase> purchaseList) {
        List<PurchaseDTO> purchaseDTOList = new ArrayList<>();
        purchaseList
                .forEach(purchase -> purchaseDTOList.add(PurchaseDTO.builder()
                        .purchaseName(purchase.getPurchaseName())
                        .countPurchases(purchase.getCountPurchases())
                        .moneyDTO(MoneyDTO.builder()
                                .currency(purchase.getMoney().getCurrency())
                                .amount(purchase.getMoney().getAmount())
                                .build())
                        .build()));
        return purchaseDTOList;
    }

    public static List<ProductDTO> productListIntoProductDTOList(List<Product> productList) {
        List<ProductDTO> productDTOList = new ArrayList<>();
        productList
                .forEach(product -> productDTOList.add(ProductDTO.builder()
                        .productName(product.getProductName())
                        .availableCountProducts(product.getAvailableCountProducts())
                        .moneyDTO(MoneyDTO.builder()
                                .currency(product.getMoney().getCurrency())
                                .amount(product.getMoney().getAmount())
                                .build())
                        .build()));
        return productDTOList;
    }
}
