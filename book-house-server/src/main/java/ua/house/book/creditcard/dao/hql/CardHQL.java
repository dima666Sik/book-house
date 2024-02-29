package ua.house.book.creditcard.dao.hql;

public class CardHQL {
    public static final String FIND_CARD_BY_CARD_NUMBER =
            """
                    From Cards c where c.numberCard = :numberCard
                    """;
    public static final String FIND_CARD_BY_ACCOUNT_ID =
            """
                    From Cards c where c.account.id = :idAccount
                    """;
}
