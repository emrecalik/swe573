package com.emrecalik.swe573.server.service;

import com.emrecalik.swe573.server.domain.WikiItem;

public interface WikiItemService {
    void deleteWikiItem(WikiItem wikiItem);

    boolean existsByEntityId(String entityId);

    WikiItem getByEntityId(String entityId);

    WikiItem getById(Long id);
}
