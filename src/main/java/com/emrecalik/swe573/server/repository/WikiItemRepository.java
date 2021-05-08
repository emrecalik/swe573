package com.emrecalik.swe573.server.repository;

import com.emrecalik.swe573.server.domain.WikiItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WikiItemRepository extends JpaRepository<WikiItem, Long> {
    boolean existsByEntityId(String entityId);

    Optional<WikiItem> getByEntityId(String entityId);
}
