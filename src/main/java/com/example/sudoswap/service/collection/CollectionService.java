package com.example.sudoswap.service.collection;

import com.example.sudoswap.entity.nft.CollectionEntity;

import java.util.List;

public interface CollectionService {

    public void createCollection(String name, int numberOfCards);

    public void editCollection(Long id, String name, int numberOfCards) throws Exception;

    public void deleteCollection(Long id) throws Exception;

    public List<CollectionEntity> findAll();

    public CollectionEntity findById(Long id) throws Exception;
}
