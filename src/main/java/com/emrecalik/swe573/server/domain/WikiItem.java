package com.emrecalik.swe573.server.domain;

import lombok.*;

import javax.persistence.*;
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
}
