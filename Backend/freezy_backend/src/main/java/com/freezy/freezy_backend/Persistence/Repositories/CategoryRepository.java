package com.freezy.freezy_backend.Persistence.Repositories;

import com.freezy.freezy_backend.Persistence.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findCategoryById(Long id);

    boolean existsByName(String name);

    @Query("SELECT c FROM Category c JOIN FETCH c.items WHERE c.id = (:id)")
    Category findCategoryByIdWithItems(Long id);
}
