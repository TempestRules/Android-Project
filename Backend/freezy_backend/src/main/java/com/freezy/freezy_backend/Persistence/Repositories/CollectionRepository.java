package com.freezy.freezy_backend.Persistence.Repositories;

import com.freezy.freezy_backend.Persistence.Entities.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface CollectionRepository extends JpaRepository<Collection, Long> {

    Collection findCollectionById(Long id);

    @Query("SELECT COUNT(collection_token) FROM Collection WHERE collection_token = (:token)")
    int countByCollection_token(UUID token);
}
