package com.freezy.freezy_backend.Persistence.Repositories;

import com.freezy.freezy_backend.Persistence.Entities.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Collection, Long> {
}
