package ua.house.book.creditcard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.house.book.auth.dao.AccountDAO;
import ua.house.book.auth.domain.entity.Account;
import ua.house.book.auth.exception.UserNotFoundException;
import ua.house.book.core.domain.dto.request.MoneyDTO;
import ua.house.book.core.domain.entity.Money;
import ua.house.book.creditcard.dao.CardDAO;
import ua.house.book.creditcard.domain.dto.request.CardDTO;
import ua.house.book.creditcard.domain.dto.request.MoneyCardDTO;
import ua.house.book.creditcard.domain.entity.Cards;
import ua.house.book.creditcard.domain.entity.MoneyCards;
import ua.house.book.creditcard.exception.CardNotFoundException;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CardServiceImpl implements CardService {
    private final CardDAO cardDAO;
    private final AccountDAO accountDAO;

    private Account findAccount(Authentication authentication){
        String userEmail = authentication.getName();
        return accountDAO.findAccountByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("User with email " + userEmail + " not found!"));

    }
    private Cards initCard(Authentication authentication, CardDTO cardDTO) {
        Money money = Money.builder()
                .amount(cardDTO.getMoneyCard().getMoneyDTO().getAmount())
                .currency(cardDTO.getMoneyCard().getMoneyDTO().getCurrency())
                .build();
        MoneyCards moneyCards = MoneyCards.builder()
                .money(money)
                .spendLimit(cardDTO.getMoneyCard().getSpendLimit())
                .build();
        return Cards.builder()
                .moneyCards(moneyCards)
                .numberCard(cardDTO.getNumberCard())
                .cardEndDataMonth(cardDTO.getCardEndDataMonth())
                .cardEndDataYear(cardDTO.getCardEndDataYear())
                .cvc2(cardDTO.getCvc2())
                .account(findAccount(authentication))
                .build();
    }

    @Transactional
    @Override
    public void createCard(Authentication authentication, CardDTO cardDTO) {
        Cards cards = initCard(authentication, cardDTO);
        cardDAO.saveCard(cards);
    }

    @Override
    public CardDTO readCard(Authentication authentication, CardDTO cardDTO) {
        Account account = findAccount(authentication);
        Cards cards = cardDAO.getCard(account.getId())
                .orElseThrow(() -> new CardNotFoundException("Not found card with this numberCard: "
                        + cardDTO.getNumberCard()));
        return CardDTO
                .builder()
                .numberCard(cards.getNumberCard())
                .moneyCard(MoneyCardDTO.builder()
                        .spendLimit(cards.getMoneyCards().getSpendLimit())
                        .moneyDTO(MoneyDTO.builder()
                                .amount(cards.getMoneyCards().getMoney().getAmount())
                                .currency(cards.getMoneyCards().getMoney().getCurrency())
                                .build())
                        .build())
                .cardEndDataMonth(cards.getCardEndDataMonth())
                .cardEndDataYear(cards.getCardEndDataMonth())
                .cvc2(cards.getCvc2())
                .build();
    }
}
