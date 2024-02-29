package ua.house.book.auth.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "ua.house.book.auth.dao",
        "ua.house.book.core.dao",
        "ua.house.book.core.service",
        "ua.house.book.creditcard.dao"
})
public class TestAuthConfig {

}