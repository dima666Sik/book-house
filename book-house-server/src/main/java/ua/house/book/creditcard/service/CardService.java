package ua.house.book.creditcard.service;

import org.springframework.security.core.Authentication;
import ua.house.book.creditcard.domain.dto.request.CardDTO;

public interface CardService {

    CardDTO readCard(Authentication authentication, CardDTO cardDTO);
    void createCard(Authentication authentication, CardDTO cardDTO);
}
