package ua.house.book.core.dao.hql;

public class ProductHQL {
    public static final String GET_ALL_PRODUCTS =
            """
                       FROM Product
                    """;
    public static final String GET_ALL_PRODUCTS_ASC =
            """
                       FROM Product p order by p.productName asc
                    """;
    ;
    public static final String GET_ALL_PRODUCTS_DESC =
            """
                       FROM Product p order by p.productName desc
                    """;

    private ProductHQL() {
    }


}
