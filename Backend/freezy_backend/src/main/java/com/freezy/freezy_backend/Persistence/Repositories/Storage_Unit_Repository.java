package com.freezy.freezy_backend.Persistence.Repositories;

import com.freezy.freezy_backend.Persistence.Entities.Storage_Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface Storage_Unit_Repository extends JpaRepository<Storage_Unit, Long> {

    Storage_Unit findStorage_UnitById(Long id);

    boolean existsByName(String name);

    @Query("SELECT s FROM Storage_Unit s JOIN FETCH s.items WHERE s.id = (:id)")
    Storage_Unit findStorage_UnitByIdWithItems(Long id);
}
