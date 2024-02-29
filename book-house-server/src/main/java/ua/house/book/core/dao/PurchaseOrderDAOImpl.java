package ua.house.book.core.dao;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.house.book.auth.domain.entity.Account;
import ua.house.book.core.dao.hql.OrderHQL;
import ua.house.book.core.domain.entity.Order;
import ua.house.book.core.domain.entity.Purchase;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
@Transactional(readOnly = true)
public class PurchaseOrderDAOImpl implements PurchaseOrderDAO {
    private final SessionFactory sessionFactory;
    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    @Override
    public void createListOrder(List<Order> orderList) {
        orderList
                .forEach(order -> currentSession().persist(order));
    }

    @Override
    public List<Order> getListOrder(Long idAccount) {
        return currentSession()
                .createQuery(OrderHQL.GET_ALL_ORDER_BY_ACCOUNT_ID, Order.class)
                .setParameter("idAccount", idAccount)
                .getResultList();
    }
}
