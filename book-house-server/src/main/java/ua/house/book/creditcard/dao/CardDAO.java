package ua.house.book.creditcard.dao;

import ua.house.book.creditcard.domain.entity.Card;

import java.util.Optional;

public interface CardDAO {
    void saveCard(Card card);
    Optional<Card> getCard(Long idAccount);
}
