package com.example.sudoswap.service.collection.impl;

import com.example.sudoswap.entity.nft.CardEntity;
import com.example.sudoswap.entity.nft.CollectionEntity;
import com.example.sudoswap.repository.CardRepository;
import com.example.sudoswap.repository.CollectionRepository;
import com.example.sudoswap.service.collection.CollectionService;
import com.example.sudoswap.service.common.CommonService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {

    private final CollectionRepository collectionRepository;
    private final CardRepository cardRepository;
    private final CommonService commonService;

    public CollectionServiceImpl(CollectionRepository collectionRepository, CardRepository cardRepository, CommonService commonService) {
        this.collectionRepository = collectionRepository;
        this.cardRepository = cardRepository;
        this.commonService = commonService;
    }

    @Override
    public void createCollection(String name, int numberOfCards) {
        collectionRepository.save(new CollectionEntity(name, numberOfCards));
    }

    @Override
    public void editCollection(Long id, String name, int numberOfCards) throws Exception {
        CollectionEntity collection = collectionRepository.findById(id).orElseThrow(Exception::new);
        collection.setName(name);
        collection.setNumberOfCards(numberOfCards);
        collectionRepository.save(collection);
    }

    @Override
    public void deleteCollection(Long id) throws Exception {
        CollectionEntity collection = collectionRepository.findById(id).orElseThrow(Exception::new);
        List<CardEntity> cards = cardRepository.findAllByCollection(collection);
        for (CardEntity c : cards) {
            if (c.getUser().getId() != commonService.getAdminUser().getId()) {
                throw new Exception("Error: Some of the cards are owned by other users !");
            }
        }
        collectionRepository.deleteById(id);
    }

    @Override
    public List<CollectionEntity> findAll() {
        return collectionRepository.findAll();
    }

    @Override
    public CollectionEntity findById(Long id) throws Exception {
        return collectionRepository.findById(id).orElseThrow(Exception::new);
    }
}
