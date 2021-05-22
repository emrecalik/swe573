package com.emrecalik.wikimed.server.service;

import com.emrecalik.wikimed.server.model.response.WikiApiResponseDto;
import com.emrecalik.wikimed.server.service.api.wikidata.WikidataApi;
import com.emrecalik.wikimed.server.service.mapper.WikiItemMapper;
import org.springframework.stereotype.Service;
import org.wikidata.wdtk.datamodel.interfaces.EntityDocument;
import org.wikidata.wdtk.wikibaseapi.WbSearchEntitiesResult;

import java.util.LinkedList;
import java.util.List;

@Service
public class WikiApiServiceImpl implements WikiApiService {

    private final WikidataApi wikidataApi;

    public WikiApiServiceImpl(WikidataApi wikidataApi) {
        this.wikidataApi = wikidataApi;
    }

    @Override
    public List<WikiApiResponseDto> getWikiItems(String query) {
        List<WbSearchEntitiesResult> wbSearchEntitiesResults = wikidataApi.getWikidataItems(query);
        List<WikiApiResponseDto> wikiApiResponseDtoList = new LinkedList<>();
        for (WbSearchEntitiesResult entitiesResult : wbSearchEntitiesResults) {
            wikiApiResponseDtoList.add(WikiItemMapper.convertWbSearchEntitiesResultToWikiItemDto(entitiesResult));
        }
        return wikiApiResponseDtoList;
    }

    @Override
    public WikiApiResponseDto getWikiItemById(String id) {
        EntityDocument entityDocument = wikidataApi.getWikidataByItem(id);
        return WikiItemMapper.convertEntityDocumentToWikiItemDto(entityDocument);
    }
}
