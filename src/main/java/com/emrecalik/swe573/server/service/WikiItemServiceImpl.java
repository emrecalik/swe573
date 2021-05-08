package com.emrecalik.swe573.server.service;

import com.emrecalik.swe573.server.domain.WikiItem;
import com.emrecalik.swe573.server.exception.ResourceNotFoundException;
import com.emrecalik.swe573.server.repository.WikiItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WikiItemServiceImpl implements WikiItemService {

    private static final String WIKI_ITEM_COULD_NOT_BE_FOUND = "Wiki Item could not be found!";

    private final WikiItemRepository wikiItemRepository;

    public WikiItemServiceImpl(WikiItemRepository wikiItemRepository) {
        this.wikiItemRepository = wikiItemRepository;
    }

    @Override
    public boolean existsByEntityId(String entityId) {
        return wikiItemRepository.existsByEntityId(entityId);
    }

    @Override
    public WikiItem getById(Long id) {
        return wikiItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(WikiItemServiceImpl.WIKI_ITEM_COULD_NOT_BE_FOUND));
    }

    @Override
    public void deleteWikiItem(WikiItem wikiItem) {
        wikiItemRepository.delete(wikiItem);
        log.info("Wiki Item is deleted.");
    }

    @Override
    public WikiItem getByEntityId(String entityId) {
        return wikiItemRepository.getByEntityId(entityId)
                .orElseThrow(() -> new ResourceNotFoundException(WikiItemServiceImpl.WIKI_ITEM_COULD_NOT_BE_FOUND));
    }


}
