package com.emrecalik.swe573.server.repository;

import com.emrecalik.swe573.server.domain.purearticle.PureArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PureArticleRepository extends JpaRepository<PureArticle, Long> {
    @Query("SELECT p FROM PureArticle p WHERE p.articleAbstract LIKE %:keyword%")
    Page<PureArticle> findPageByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
