package ua.house.book.auth.dao.hql;

public class AccountHQL {
    public static final String FIND_USER_BY_EMAIL_AND_PASSWORD =
            "SELECT a FROM User a WHERE a.email = :email AND a.password = :password";

    public static final String FIND_ADMIN_BY_EMAIL_AND_PASSWORD =
            "SELECT a FROM Admin a WHERE a.email = :email AND a.password = :password";
    private AccountHQL() {
    }

}
