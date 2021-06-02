package com.emrecalik.wikimed.server.service.api.wikidata;

import com.emrecalik.wikimed.server.exception.ExternalApiException;
import org.springframework.stereotype.Component;
import org.wikidata.wdtk.datamodel.interfaces.EntityDocument;
import org.wikidata.wdtk.wikibaseapi.WbSearchEntitiesResult;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataFetcher;
import org.wikidata.wdtk.wikibaseapi.apierrors.MediaWikiApiErrorException;

import java.io.IOException;
import java.util.List;

@Component
public class WikidataApi {

    private static final String WIKIDATA_API_DOES_NOT_RESPOND = "Wikidata API does not respond!";

    public List<WbSearchEntitiesResult> getWikidataItems(String query) {
        WikibaseDataFetcher wikidataDataFetcher = WikibaseDataFetcher.getWikidataDataFetcher();
        try {
            return wikidataDataFetcher.searchEntities(query);
        } catch (MediaWikiApiErrorException | IOException wikiApiErrorException) {
            throw new ExternalApiException(WikidataApi.WIKIDATA_API_DOES_NOT_RESPOND);
        }
    }

    public EntityDocument getWikidataByItem(String id) {
        WikibaseDataFetcher wikibaseDataFetcher = WikibaseDataFetcher.getWikidataDataFetcher();
        try {
            return wikibaseDataFetcher.getEntityDocument(id);
        } catch (MediaWikiApiErrorException | IOException wikiApiErrorException) {
            throw new ExternalApiException(WikidataApi.WIKIDATA_API_DOES_NOT_RESPOND);
        }
    }
}
