package com.emrecalik.wikimed.server.repository;

import com.emrecalik.wikimed.server.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    boolean existsByUserId(Long userId);
    RefreshToken findByUserId(Long userId);
}
