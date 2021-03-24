package com.freezy.freezy_backend.Persistence.Repositories;

import com.freezy.freezy_backend.Persistence.Entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
}
