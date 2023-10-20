package ua.house.book.auth.cofig;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ua.house.book.auth.domain.entity.Admin;
import ua.house.book.auth.domain.entity.User;

@Configuration
@ComponentScan("ua.house.book.auth")
public class AuthConfig {

}
