package ua.house.book.auth.dao;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.house.book.auth.domain.entity.Account;
import ua.house.book.auth.dao.hql.AccountHQL;
import ua.house.book.auth.domain.entity.User;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Transactional(readOnly = true)
public class AccountDAOImpl implements AccountDAO {
    private final SessionFactory sessionFactory;

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    @Override
    public void saveAccount(Account account) {
        currentSession().merge(account);
    }

    @Override
    public Optional<Account> findUserAccountByEmailAndPassword(String email, String password) {
        var resultSearch = currentSession()
                .createQuery(AccountHQL.FIND_USER_BY_EMAIL_AND_PASSWORD, Account.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .uniqueResult();
        var resultSearchFull = currentSession()
                .createQuery(AccountHQL.FIND_ORDERS_FOR_ACCOUNT, Account.class)
                .setParameter("account", resultSearch)
                .uniqueResult();
        return Optional.ofNullable(resultSearchFull);
    }

    @Override
    public Optional<Account> findAdminAccountByEmailAndPassword(String email, String password) {
        var resultSearch = currentSession()
                .createQuery(AccountHQL.FIND_ADMIN_BY_EMAIL_AND_PASSWORD, Account.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .uniqueResult();
        var resultSearchFull = currentSession()
                .createQuery(AccountHQL.FIND_ORDERS_FOR_ACCOUNT, Account.class)
                .setParameter("account", resultSearch)
                .uniqueResult();
        return Optional.ofNullable(resultSearchFull);
    }

    @Override
    public Optional<Account> findAccountByEmail(String email) {
        var resultSearch = currentSession()
                .createQuery(AccountHQL.FIND_USER_BY_EMAIL, Account.class)
                .setParameter("email", email)
                .uniqueResult();
        return Optional.ofNullable(resultSearch);
    }
}
