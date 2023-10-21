package ua.house.book.auth.dao;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.house.book.auth.domain.entity.Account;
import ua.house.book.auth.dao.hql.AccountHQL;

import java.util.Optional;

@AllArgsConstructor
@Repository
@Transactional(readOnly = true)
public class AccountDAOImpl implements AccountDAO {
    private final SessionFactory sessionFactory;

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    @Override
    public void createAccount(Account account) {
        currentSession().merge(account);
    }

    @Override
    public Optional<Account> findAccountByEmailAndPassword(String email, String password, Class<? extends Account> clazz) {
        return Optional.ofNullable(
                currentSession()
                        .createQuery(AccountHQL.findAccountByEmailAndPassword(clazz), clazz)
                        .setParameter("email", email)
                        .setParameter("password", password)
                        .getSingleResult());
    }
}
