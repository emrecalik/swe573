package com.emrecalik.swe573.server.repository;

import com.emrecalik.swe573.server.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT a FROM Article a WHERE a.user.id = :userId")
    Page<Article> findAllByUserId(@Param("userId") Long userId, Pageable pageable);

    Page<Article> findAll(Pageable pageable);

    boolean existsByEntityIdAndUserId(Long entityId, Long userId);

    Optional<Article> findByEntityIdAndUserId(Long entityId, Long userId);
}
