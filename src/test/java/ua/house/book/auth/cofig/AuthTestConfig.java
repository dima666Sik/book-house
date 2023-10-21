package ua.house.book.auth.cofig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ua.house.book.auth.domain.Role;
import ua.house.book.auth.domain.entity.Account;
import ua.house.book.auth.domain.entity.Admin;
import ua.house.book.auth.domain.entity.User;
import ua.house.book.auth.dao.AccountDAOImplTest;
import ua.house.book.auth.service.AuthServiceImplTest;

import java.util.Set;

@Configuration
public class AuthTestConfig {
    @Bean
    public AccountDAOImplTest accountRepositoryImplTest() {
        return new AccountDAOImplTest();
    }

    @Bean
    public AuthServiceImplTest authServiceImplTest() {
        return new AuthServiceImplTest();
    }

    @Bean
    public Account userAccount() {
        return User.builder()
                .id(null)
                .email("dimon@gmail.com")
                .password("qwerty")
                .username("dimas")
                .roleSet(Set.of(Role.USER))
                .build();
    }

    @Bean
    public Account adminAccount() {
        return Admin.builder()
                .id(null)
                .email("admin@gmail.com")
                .password("admin")
                .username("admin")
                .roleSet(Set.of(Role.USER, Role.ADMIN))
                .build();
    }
}