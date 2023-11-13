package ua.house.book.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import ua.house.book.auth.domain.Role;
import ua.house.book.auth.domain.dto.request.AuthorizationDTO;
import ua.house.book.auth.domain.dto.request.RegistrationDTO;
import ua.house.book.auth.domain.entity.Account;
import ua.house.book.auth.domain.entity.Admin;
import ua.house.book.auth.domain.entity.User;
import ua.house.book.auth.dao.AccountDAOImplTest;
import ua.house.book.auth.service.AuthServiceImplTest;
import ua.house.book.auth.service.jwt.JwtService;

import java.util.Set;

@Configuration
@ComponentScan("ua.house.book.auth.dao")

public class TestAuthConfig {
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
        return Account.builder()
                .id(null)
                .email("dimon@gmail.com")
                .password("qwerty")
                .nickname("dimas")
                .roles(Set.of(Role.builder().nameRole("USER").build()))
                .user(User.builder().build())
                .build();
    }

    @Bean
    public Account adminAccount() {
        return Account.builder()
                .id(null)
                .email("admin@gmail.com")
                .password("admin")
                .nickname("admin")
                .roles(Set.of(Role.builder().nameRole("ADMIN").build()))
                .admin(Admin.builder().build())
                .build();
    }

    @Bean
    public RegistrationDTO userAccountRegistrationDtoRequest() {
        return RegistrationDTO.builder()
                .email("dimon@gmail.com")
                .password("qwerty")
                .username("dimas")
                .build();
    }

    @Bean
    public RegistrationDTO adminAccountRegistrationDtoRequest() {
        return RegistrationDTO.builder()
                .email("admin@gmail.com")
                .password("admin")
                .username("admin")
                .build();
    }

    @Bean
    public AuthorizationDTO userAuthorizationDtoRequest() {
        return AuthorizationDTO
                .builder()
                .email("dimon@gmail.com")
                .password("qwerty")
                .build();
    }

    @Bean
    public AuthorizationDTO adminAuthorizationDtoRequest() {
        return AuthorizationDTO
                .builder()
                .email("admin@gmail.com")
                .password("admin")
                .build();
    }
}