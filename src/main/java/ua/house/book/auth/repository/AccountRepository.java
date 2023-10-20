package ua.house.book.auth.repository;

import ua.house.book.auth.domain.entity.Account;
import ua.house.book.auth.domain.entity.Admin;

import java.util.Optional;

public interface AccountRepository {
    void createAccount(final Account account);

    Optional<Account> findAccountByEmailAndPassword(String email, String password, Class<? extends Account> clazz);
}
