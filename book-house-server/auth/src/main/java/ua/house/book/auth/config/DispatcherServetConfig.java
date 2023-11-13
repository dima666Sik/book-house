package ua.house.book.auth.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import ua.house.book.auth.config.filter.JwtAuthenticationFilter;
import ua.house.book.auth.dao.AccountDAOImpl;
import ua.house.book.auth.dao.TokenDAOImpl;
import ua.house.book.auth.service.AccountUserDetailsService;
import ua.house.book.auth.service.LogoutAccountService;
import ua.house.book.auth.service.jwt.JwtService;

import javax.annotation.PostConstruct;

public class DispatcherServetConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{
                HibernateConfig.class, SecurityConfig.class,
                JwtAuthenticationFilter.class, AccountUserDetailsService.class,
                LogoutAccountService.class, JwtService.class,
                AccountDAOImpl.class, TokenDAOImpl.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{AuthConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
