package ua.house.book.core.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import ua.house.book.auth.domain.entity.Account;
import ua.house.book.auth.domain.entity.Token;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
@EqualsAndHashCode
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "order_date")
    private String orderDate;

    @Column(name = "order_time")
    private String orderTime;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "order")
    private List<Purchase> purchaseList;
}
