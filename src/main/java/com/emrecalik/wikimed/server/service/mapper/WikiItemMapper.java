package com.emrecalik.wikimed.server.service.mapper;

import com.emrecalik.wikimed.server.domain.WikiItem;
import com.emrecalik.wikimed.server.model.response.WikiApiResponseDto;
import org.wikidata.wdtk.datamodel.implementation.ItemDocumentImpl;
import org.wikidata.wdtk.datamodel.interfaces.EntityDocument;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.datamodel.interfaces.MonolingualTextValue;
import org.wikidata.wdtk.wikibaseapi.WbSearchEntitiesResult;

import java.util.HashSet;
import java.util.stream.Collectors;

public class WikiItemMapper {

    private WikiItemMapper() {
    }

    public static WikiApiResponseDto convertWbSearchEntitiesResultToWikiItemDto(WbSearchEntitiesResult
                                                                                        entitiesResult) {
        return WikiApiResponseDto.builder()
                .entityId(entitiesResult.getEntityId())
                .conceptUri(entitiesResult.getConceptUri())
                .label(entitiesResult.getLabel())
                .description(entitiesResult.getDescription())
                .aliases(entitiesResult.getAliases())
                .build();
    }

    public static WikiApiResponseDto convertEntityDocumentToWikiItemDto(EntityDocument entityDocument) {

        ItemDocument itemDocument = (ItemDocumentImpl) entityDocument;
        return WikiApiResponseDto.builder()
                .entityId(entityDocument.getEntityId().getId())
                .conceptUri(entityDocument.getEntityId().getIri())
                .label(itemDocument.getLabels().get("en").getText())
                .description(itemDocument.getDescriptions().get("en") == null ? null :
                        itemDocument.getDescriptions().get("en").getText())
                .aliases(itemDocument.getAliases().get("en") == null ? null :
                        itemDocument.getAliases().get("en").stream()
                                .map(MonolingualTextValue::getText)
                                .collect(Collectors.toList()))
                .build();
    }

    public static WikiItem convertWikiItemApiDtoToWikiItem(WikiApiResponseDto wikiApiResponseDto) {
        return WikiItem.builder()
                .entityId(wikiApiResponseDto.getEntityId())
                .conceptUri(wikiApiResponseDto.getConceptUri())
                .label(wikiApiResponseDto.getLabel())
                .description(wikiApiResponseDto.getDescription())
                .aliases(wikiApiResponseDto.getAliases() == null ? null :
                        new HashSet<>(wikiApiResponseDto.getAliases()))
                .build();
    }
}
