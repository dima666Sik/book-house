package ua.house.book.creditcard.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import ua.house.book.core.domain.entity.Money;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "money_card")
@EqualsAndHashCode
public class MoneyCards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;
    @Column(name = "spend_limit")
    private Integer spendLimit;
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "moneyCards")
    private Card card;

    @EqualsAndHashCode.Exclude
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "money_id", referencedColumnName = "id")
    private Money money;
}