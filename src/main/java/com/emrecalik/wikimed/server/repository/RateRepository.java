package com.emrecalik.wikimed.server.repository;

import com.emrecalik.wikimed.server.domain.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
    Optional<Rate> findByUserIdAndArticleId(Long userId, Long articleId);
}
