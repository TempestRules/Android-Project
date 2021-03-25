package com.freezy.freezy_backend.Persistence.Repositories;

import com.freezy.freezy_backend.Persistence.Entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("SELECT t FROM Token t JOIN FETCH t.account_login WHERE t.token = (:token)")
    Token getTokenByTokenWithAccountLogin(UUID token);

    Token getTokenById(Long id);
}
