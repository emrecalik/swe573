package com.emrecalik.swe573.server.service.api.entrez;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@NoArgsConstructor
@XmlRootElement(name = "eSearchResult")
public class ArticleIdList {

    @XmlElementWrapper(name = "IdList")
    @XmlElement(name = "Id")
    private List<String> idList;
}

