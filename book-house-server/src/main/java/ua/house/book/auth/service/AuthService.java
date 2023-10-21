package ua.house.book.auth.service;

import ua.house.book.auth.domain.entity.Account;
import ua.house.book.auth.domain.entity.Admin;

import java.util.Optional;

public interface AuthService {
    boolean registration(final Account account);
    Optional<Account> authorization(String email, String password, Class<? extends Account> clazz);
}
