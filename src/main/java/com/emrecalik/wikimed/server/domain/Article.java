package com.emrecalik.wikimed.server.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "article", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "entity_id",
                "user_id"
        })
})
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "entity_id")
    private Long entityId;

    @Lob
    private String title;

    @Lob
    private String articleAbstract;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "article_id")
    private Set<ArticleAuthor> authors = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "article_keyword",
            joinColumns = @JoinColumn(name = "article_id"))
    @Column(name = "keyword")
    private Set<String> keywords = new HashSet<>();

    @JsonBackReference
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "article_wiki_item",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "wiki_item_id"))
    private Set<WikiItem> wikiItems = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "article_id")
    private Set<Rate> rates = new HashSet<>();
}
