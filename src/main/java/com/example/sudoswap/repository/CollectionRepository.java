package com.example.sudoswap.repository;

import com.example.sudoswap.entity.nft.CardEntity;
import com.example.sudoswap.entity.nft.CollectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CollectionRepository extends JpaRepository<CollectionEntity, Long> {

    Optional<CollectionEntity> findByCardsContaining(CardEntity card);
}
