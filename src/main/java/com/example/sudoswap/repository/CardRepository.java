package com.example.sudoswap.repository;

import com.example.sudoswap.entity.nft.CardEntity;
import com.example.sudoswap.entity.nft.CollectionEntity;
import com.example.sudoswap.entity.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardRepository extends JpaRepository<CardEntity, Long> {

    List<CardEntity> findAllByUser(UserEntity user);

    List<CardEntity> findAllByCollection(CollectionEntity collection);

    Page<CardEntity> findAllBySellFlag(boolean sellFlag, Pageable pageable);

    Page<CardEntity> findAllByUser(UserEntity user, Pageable pageable);

    Page<CardEntity> findAllByNameContainingAndSellFlag(String name, boolean sellFlag, Pageable pageable);

    Page<CardEntity> findAllByCollectionAndSellFlag(CollectionEntity collection, boolean sellFlag, Pageable pageable);

    Page<CardEntity> findAllByNameContainingAndSellFlagAndUser(String name, boolean sellFlag, UserEntity user, Pageable pageable);

    Page<CardEntity> findAllByCollectionAndSellFlagAndUser(CollectionEntity collection, boolean sellFlag, UserEntity user, Pageable pageable);

    @Query("SELECT c FROM CardEntity c WHERE c.id IN " +
            "(SELECT MIN(cc.id) FROM CardEntity cc GROUP BY cc.collection.id)")
    List<CardEntity> getOneCardFromEachCollection();




}
