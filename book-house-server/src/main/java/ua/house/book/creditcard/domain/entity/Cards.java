package ua.house.book.creditcard.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import ua.house.book.auth.domain.entity.Account;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cards")
@EqualsAndHashCode
public class Cards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;
    @Column(name = "number_card")
    @UniqueElements
    private String numberCard;
    @Column(name = "card_end_data_month")
    private Short cardEndDataMonth;
    @Column(name = "card_end_data_year")
    private Short cardEndDataYear;
    private String cvc2;

    @EqualsAndHashCode.Exclude
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "money_card_id", referencedColumnName = "id")
    private MoneyCards moneyCards;

    @EqualsAndHashCode.Exclude
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;
}
