package ua.house.book.auth.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import ua.house.book.auth.domain.entity.Account;

@SuperBuilder
@AllArgsConstructor
@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper=true)
public class User extends Account {

}
