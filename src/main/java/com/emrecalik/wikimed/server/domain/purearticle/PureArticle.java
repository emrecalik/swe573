package com.emrecalik.wikimed.server.domain.purearticle;

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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pure_article")
public class PureArticle {

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
    @JoinColumn(name = "pure_article_id")
    private Set<PureArticleAuthor> authors = new HashSet<>();

    @Lob
    @ElementCollection
    @CollectionTable(name = "pure_article_keyword",
            joinColumns = @JoinColumn(name = "pure_article_id"))
    @Column(name = "keyword")
    private Set<String> keywords = new HashSet<>();
}
