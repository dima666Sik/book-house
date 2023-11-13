package ua.house.book.auth.dao;

import ua.house.book.auth.domain.entity.Token;

import java.util.List;
import java.util.Optional;

public interface TokenDAO {
    Optional<Token> findByToken(String token);
    void saveToken(Token token);

    List<Token> findAllValidTokenByAccount(Long id);

    void saveAllTokens(List<Token> validUserTokens);
}
