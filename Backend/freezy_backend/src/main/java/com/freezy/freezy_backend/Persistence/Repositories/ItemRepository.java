package com.freezy.freezy_backend.Persistence.Repositories;

import com.freezy.freezy_backend.Persistence.Entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
