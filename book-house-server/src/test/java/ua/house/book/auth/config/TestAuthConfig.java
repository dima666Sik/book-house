package ua.house.book.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ua.house.book.auth.domain.Role;
import ua.house.book.auth.domain.dto.request.AuthorizationDTO;
import ua.house.book.auth.domain.dto.request.RegistrationDTO;
import ua.house.book.auth.domain.entity.Account;
import ua.house.book.auth.domain.entity.User;
import ua.house.book.auth.dao.AccountDAOImplTest;
import ua.house.book.auth.service.AuthServiceImplTest;

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
        return User.builder()
                .id(null)
                .email("admin@gmail.com")
                .password("admin")
                .username("admin")
                .roleSet(Set.of(Role.ADMIN))
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