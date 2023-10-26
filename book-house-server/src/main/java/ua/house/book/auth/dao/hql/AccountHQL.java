package ua.house.book.auth.dao.hql;

import ua.house.book.auth.domain.entity.Account;

public class AccountHQL {
    private AccountHQL() {
    }

    public static String findAccountByEmailAndPassword(Class<? extends Account> clazz) {
        return "SELECT a FROM " + clazz.getSimpleName() + " a WHERE a.email = :email AND a.password = :password";
    }

    public static String findAll(Class<? extends Account> clazz) {
        return "SELECT a FROM " + clazz.getSimpleName() + " a ";
    }
}
