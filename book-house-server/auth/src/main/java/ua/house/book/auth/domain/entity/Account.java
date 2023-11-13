package ua.house.book.auth.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.house.book.auth.domain.Role;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "accounts")
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;
    private String nickname;
    @Column(unique = true)
    private String email;
    private String password;

    @EqualsAndHashCode.Exclude
    @ElementCollection
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "account_id"))
    private Set<Role> roles;

    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "account")
    private User user;

    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "account")
    private Admin admin;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "account")
    private List<Token> tokenList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
