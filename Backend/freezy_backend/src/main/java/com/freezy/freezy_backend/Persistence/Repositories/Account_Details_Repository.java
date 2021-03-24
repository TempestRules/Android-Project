package com.freezy.freezy_backend.Persistence.Repositories;

import com.freezy.freezy_backend.Persistence.Entities.Account_Details;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Account_Details_Repository extends JpaRepository<Account_Details, Long> {
}
