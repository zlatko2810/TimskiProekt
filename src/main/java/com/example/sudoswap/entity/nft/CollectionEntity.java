package com.example.sudoswap.entity.nft;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "collections")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CollectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "number_of_cards")
    private int numberOfCards;

    @OneToMany(mappedBy = "collection",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CardEntity> cards = new ArrayList<>();

    public CollectionEntity(String name, int numberOfCards) {
        this.name = name;
        this.numberOfCards = numberOfCards;
    }
}
