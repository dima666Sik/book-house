package ua.house.book.core.dao.hql;

public class PurchaseHQL {
    public static final String GET_ALL_PURCHASE_BY_ACCOUNT_ID =
            """
                    From Purchase p where p.order.account.id = :idAccount
                    """;

    private PurchaseHQL(){

    }
}
