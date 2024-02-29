package ua.house.book.creditcard.dao;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.house.book.creditcard.dao.hql.CardHQL;
import ua.house.book.creditcard.domain.entity.Cards;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Transactional(readOnly = true)
public class CardDAOImpl implements CardDAO {
    private final SessionFactory sessionFactory;

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    @Override
    public void saveCard(Cards cards) {
        currentSession().merge(cards);
    }

    @Override
    public Optional<Cards> getCard(Long idAccount) {
        return Optional
                .ofNullable(currentSession()
                        .createQuery(CardHQL.FIND_CARD_BY_ACCOUNT_ID, Cards.class)
                        .setParameter("idAccount", idAccount)
                        .uniqueResult());
    }

}
