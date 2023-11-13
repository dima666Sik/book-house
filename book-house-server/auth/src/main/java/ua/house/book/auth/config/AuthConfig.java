package ua.house.book.auth.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.PostConstruct;

@Configuration
@ComponentScan(basePackages = {
        "ua.house.book.auth.aspect",
        "ua.house.book.auth.config",
        "ua.house.book.auth.dao",
        "ua.house.book.auth.controller",
        "ua.house.book.auth.domain",
        "ua.house.book.auth.service"
})
@EnableWebMvc
@EnableAspectJAutoProxy
public class AuthConfig {
    @PostConstruct
    public void init(){}
}
