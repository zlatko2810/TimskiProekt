package com.example.sudoswap.service.pool.impl;

import com.example.sudoswap.entity.nft.CardEntity;
import com.example.sudoswap.repository.CardRepository;
import com.example.sudoswap.service.pool.PoolService;
import org.springframework.stereotype.Service;

@Service
public class PoolServiceImpl implements PoolService {

    private final CardRepository cardRepository;

    public PoolServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public void addCardToSale(Long id, float price) throws Exception {
        CardEntity card = cardRepository.findById(id).orElseThrow(Exception::new);
        card.setSellFlag(true);
        card.setPrice(price);
        cardRepository.save(card);
    }

    @Override
    public void removeCardFromSale(Long id) throws Exception {
        CardEntity card = cardRepository.findById(id).orElseThrow(Exception::new);
        card.setSellFlag(false);
        cardRepository.save(card);
    }
}
