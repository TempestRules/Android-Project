package com.freezy.freezy_backend.Persistence.Repositories;

import com.freezy.freezy_backend.Persistence.Entities.Account_Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface Account_Details_Repository extends JpaRepository<Account_Details, Long> {

    @Query("SELECT a FROM Account_Details a JOIN FETCH a.collections WHERE a.id = (:id)")
    Account_Details getAccount_DetailsByIdWithCollection(Long id);
}
