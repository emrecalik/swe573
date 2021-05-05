package com.emrecalik.swe573.server.service.api.entrez;

import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

public class PubmedArticle {

    @XmlElement(name = "MedlineCitation")
    private MedlineCitation medlineCitation;

    public MedlineCitation getMedlineCitation() {
        return medlineCitation;
    }

    @Getter
    public static class MedlineCitation {

        @XmlElement(name = "PMID")
        private String id;

        @XmlElement(name = "Article")
        private Article article;

        @Getter
        public static class Article {

            @XmlElement(name = "ArticleTitle")
            private String articleTitle;

            @XmlElementWrapper(name = "Abstract")
            @XmlElement(name = "AbstractText")
            private List<String> abstractText;

            @XmlElementWrapper(name = "AuthorList")
            @XmlElement(name = "Author")
            private List<Author> authorList;

            @Getter
            public static class Author {

                @XmlElement(name = "LastName")
                private String lastName;

                @XmlElement(name = "ForeName")
                private String foreName;
            }
        }

        @XmlElementWrapper(name = "KeywordList")
        @XmlElement(name = "Keyword")
        private List<String> keywordList;
    }
}
