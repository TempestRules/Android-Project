package com.freezy.freezy_backend.Persistence.Repositories;

import com.freezy.freezy_backend.Persistence.Entities.Account_Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Account_Login_Repository extends JpaRepository<Account_Login, Long> {
}
