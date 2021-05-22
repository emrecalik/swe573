package com.emrecalik.wikimed.server.service;

import com.emrecalik.wikimed.server.domain.WikiItem;

public interface WikiItemService {
    void deleteWikiItem(WikiItem wikiItem);

    boolean existsByEntityId(String entityId);

    WikiItem getByEntityId(String entityId);

    WikiItem getById(Long id);
}
