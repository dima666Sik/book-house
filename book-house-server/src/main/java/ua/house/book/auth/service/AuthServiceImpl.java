package ua.house.book.auth.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.house.book.auth.domain.entity.Account;
import ua.house.book.auth.dao.AccountDAO;

import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {
    private final AccountDAO accountDAO;

    @Override
    @Transactional
    public boolean registration(Account account) {
        accountDAO.createAccount(account);
        return true;
    }

    @Override
    public Optional<Account> authorization(String email, String password, Class<? extends Account> clazz) {
        System.out.println("service:" + email + " " + password + " " + clazz);
        return accountDAO.findAccountByEmailAndPassword(email, password, clazz);
    }
}
