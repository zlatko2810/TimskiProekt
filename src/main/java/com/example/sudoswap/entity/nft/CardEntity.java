package com.example.sudoswap.entity.nft;

import com.example.sudoswap.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Base64;

@Entity
@Table(name = "cards")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private float price;

    private byte[] image;

    private boolean sellFlag;

    @ManyToOne
    @JoinColumn(name = "collection_id")
    private CollectionEntity collection;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public CardEntity(String name, String description, float price, byte[] image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public String getImageBase64() {
        if (image == null) {
            return null;
        }
        return Base64.getEncoder().encodeToString(image);
    }
}
