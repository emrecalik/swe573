package com.emrecalik.wikimed.server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wiki_item")
public class WikiItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String entityId;

    private String conceptUri;

    private String label;

    private String description;

    @ElementCollection
    @CollectionTable(
            name = "wiki_item_alias",
            joinColumns = @JoinColumn(name = "wiki_item_id"))
    @Column(name = "alias")
    private Set<String> aliases = new HashSet<>();

    @ManyToMany(mappedBy = "wikiItems")
    private Set<Article> articleSet = new HashSet<>();
}
