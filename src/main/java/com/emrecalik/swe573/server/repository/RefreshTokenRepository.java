package com.emrecalik.swe573.server.repository;

import com.emrecalik.swe573.server.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    boolean existsByUserId(Long userId);
    RefreshToken findByUserId(Long userId);
}
