package com.emrecalik.wikimed.server.service.api.entrez;

import lombok.Getter;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlMixed;
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
            private ArticleTitle articleTitle;

            @Getter
            public static class ArticleTitle {
                @XmlAnyElement(lax = true)
                @XmlMixed
                private List<Object> mixedContent;
            }

            @XmlElement(name = "Abstract")
            private Abstract abstractText;

            @Getter
            public static class Abstract {
                @XmlAnyElement(lax = true)
                @XmlMixed
                private List<Object> mixedContent;
            }

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
