package ua.house.book.auth.dao;

import jakarta.persistence.NoResultException;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.house.book.auth.domain.Role;
import ua.house.book.auth.domain.entity.Account;
import ua.house.book.auth.dao.hql.AccountHQL;
import ua.house.book.auth.domain.entity.User;

import java.util.Optional;
import java.util.Set;

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
        System.out.println("repos:" + email + " " + password + " " + clazz);

        Account resultSearch = null;
        try {
            System.out.print("List:");
            System.out.println(currentSession().createQuery(AccountHQL.findAll(clazz), clazz).getResultList());
            resultSearch = currentSession()
                    .createQuery(AccountHQL.findAccountByEmailAndPassword(clazz), clazz)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException noResultException) {
            noResultException.printStackTrace();
        }
        return Optional.ofNullable(resultSearch);
    }
}
