package ua.house.book.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.house.book.auth.dao.AccountDAO;
import ua.house.book.auth.dao.TokenDAO;
import ua.house.book.auth.domain.Role;
import ua.house.book.auth.domain.TokenType;
import ua.house.book.auth.domain.dto.request.AuthorizationDTO;
import ua.house.book.auth.domain.dto.request.RegistrationDTO;
import ua.house.book.auth.domain.dto.response.AuthResponseDTO;
import ua.house.book.auth.domain.entity.Account;
import ua.house.book.auth.domain.entity.Admin;
import ua.house.book.auth.domain.entity.Token;
import ua.house.book.auth.domain.entity.User;
import ua.house.book.auth.exception.UserNotFoundException;
import ua.house.book.auth.service.jwt.JwtService;

import java.util.Set;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {
    private final AccountDAO accountDAO;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenDAO tokenDAO;

    @Override
    @Transactional
    public AuthResponseDTO ordinalRegistration(RegistrationDTO accountDTORequest) {
        Account user = Account.builder()
                .email(accountDTORequest.getEmail())
                .password(passwordEncoder.encode(accountDTORequest.getPassword()))
                .nickname(accountDTORequest.getUsername())
                .roles(Set.of(Role.builder().nameRole("USER").build()))
                .user(User.builder().build())
                .build();
        accountDAO.saveAccount(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(user, jwtToken);
        return AuthResponseDTO.builder().token(jwtToken).build();
    }

    @Override
    @Transactional
    public AuthResponseDTO adminRegistration(RegistrationDTO accountDTORequest) {
        Account admin = Account.builder()
                .email(accountDTORequest.getEmail())
                .password(passwordEncoder.encode(accountDTORequest.getPassword()))
                .nickname(accountDTORequest.getUsername())
                .roles(Set.of(Role.builder().nameRole("ADMIN").build()))
                .admin(Admin.builder().build())
                .build();
        accountDAO.saveAccount(admin);
        var jwtToken = jwtService.generateToken(admin);
        saveUserToken(admin, jwtToken);
        return AuthResponseDTO.builder().token(jwtToken).build();
    }

    @Override
    public AuthResponseDTO authorization(AuthorizationDTO authorizationDTORequest) {
        var account = accountDAO.findAccountByEmail(authorizationDTORequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User with email " + authorizationDTORequest.getEmail() + " not found!"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authorizationDTORequest.getEmail(),
                        authorizationDTORequest.getPassword()
                )
        );

        var jwtToken = jwtService.generateToken(account);
        revokeAllUserTokens(account);
        saveUserToken(account, jwtToken);
        return AuthResponseDTO.builder().token(jwtToken).build();
    }

    private void saveUserToken(Account account, String jwtToken) {
        var token = Token.builder()
                .account(account)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenDAO.saveToken(token);
    }

    private void revokeAllUserTokens(Account account) {
        var validUserTokens =
                tokenDAO.findAllValidTokenByAccount(account.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenDAO.saveAllTokens(validUserTokens);
    }
}
