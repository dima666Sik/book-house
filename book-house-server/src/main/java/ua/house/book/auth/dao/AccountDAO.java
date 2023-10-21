package ua.house.book.auth.dao;

import ua.house.book.auth.domain.entity.Account;

import java.util.Optional;

public interface AccountDAO {
    void createAccount(final Account account);
    Optional<Account> findAccountByEmailAndPassword(String email, String password, Class<? extends Account> clazz);
}
