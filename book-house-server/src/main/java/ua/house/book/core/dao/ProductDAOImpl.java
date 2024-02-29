package ua.house.book.core.dao;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.house.book.auth.domain.entity.Account;
import ua.house.book.core.dao.hql.ProductHQL;
import ua.house.book.core.domain.entity.Product;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Transactional(readOnly = true)
public class ProductDAOImpl implements ProductDAO {
    private final SessionFactory sessionFactory;

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    @Override
    public Long saveProduct(Product product) {
        return currentSession().merge(product).getId();
    }

    @Override
    public Optional<Product> getProduct(Long idProduct) {
        return Optional.ofNullable(currentSession()
                .get(Product.class, idProduct));
    }

    @Transactional
    @Override
    public void updateProduct(Product product) {
        currentSession().merge(product);
    }

    @Transactional
    @Override
    public void deleteProduct(Long idProduct) {
        Product product = currentSession().get(Product.class, idProduct);
        currentSession().remove(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return currentSession()
                .createQuery(ProductHQL.GET_ALL_PRODUCTS, Product.class)
                .getResultList();
    }
    @Transactional
    @Override
    public void saveAllProducts(List<Product> productList) {
        for (Product product : productList) {
            currentSession().merge(product);
        }
    }
}
