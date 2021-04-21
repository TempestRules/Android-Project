package com.freezy.freezy_backend.Persistence.Repositories;

import com.freezy.freezy_backend.Persistence.Entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findItemById(Long id);

    @Query("SELECT i FROM Item i JOIN FETCH i.storage_unit WHERE i.id = (:id)")
    Item findItemByIdWithStorage_Unit(Long id);

    @Query("SELECT i FROM Item i JOIN FETCH  i.categories WHERE i.id = (:id)")
    Item findItemByIdWithCategories(Long id);
}
