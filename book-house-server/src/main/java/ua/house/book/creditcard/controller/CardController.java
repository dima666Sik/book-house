package ua.house.book.creditcard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ua.house.book.creditcard.domain.dto.request.CardDTO;
import ua.house.book.creditcard.service.CardService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/core/cards")
public class CardController {
    private final CardService cardService;

    @PostMapping("/create-card")
    public ResponseEntity<String> createCard(Authentication authentication, @RequestBody CardDTO cardDTO) {
        cardService.createCard(authentication, cardDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Card create " + cardDTO + " is successful");
    }

    @GetMapping("/read-card")
    public ResponseEntity<CardDTO> readCard(Authentication authentication, @RequestBody CardDTO cardDTO) {
        return ResponseEntity
                .ok()
                .body(cardService.readCard(authentication, cardDTO));
    }
}
