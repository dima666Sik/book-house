package ua.house.book.auth.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.house.book.auth.dao.AccountDAO;
import ua.house.book.auth.exception.UserNotFoundException;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class AccountUserDetailsService implements UserDetailsService {
    private final AccountDAO accountDAO;
    @PostConstruct
    public void init(){
        LogManager.getLogger(this.getClass().getName()).info("----------------"+this.getClass().getName());
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountDAO.findAccountByEmail(username)
                .orElseThrow(() -> new UserNotFoundException("Account with email: " + username + " was not found!"));
    }
}
