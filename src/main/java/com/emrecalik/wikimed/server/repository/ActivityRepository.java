package com.emrecalik.wikimed.server.repository;

import com.emrecalik.wikimed.server.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findAllByActorIdInOrderByPublishedDesc(List<Long> actorIdList);

    void deleteByObjectId(Long objectId);
}
