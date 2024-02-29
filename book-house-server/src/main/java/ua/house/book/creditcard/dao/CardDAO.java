package ua.house.book.creditcard.dao;

import ua.house.book.creditcard.domain.entity.Cards;

import java.util.Optional;

public interface CardDAO {
    void saveCard(Cards cards);
    Optional<Cards> getCard(Long idAccount);
}
