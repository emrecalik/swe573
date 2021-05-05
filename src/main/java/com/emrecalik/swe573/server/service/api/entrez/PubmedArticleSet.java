package com.emrecalik.swe573.server.service.api.entrez;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@Getter
@NoArgsConstructor
@XmlRootElement(name = "PubmedArticleSet")
public class PubmedArticleSet {

    @XmlElement(name = "PubmedArticle")
    private Set<PubmedArticle> pubmedArticleSet;
}
