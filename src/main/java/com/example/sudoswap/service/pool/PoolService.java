package com.example.sudoswap.service.pool;

public interface PoolService {

    public void addCardToSale(Long id, float price) throws Exception;

    public void removeCardFromSale(Long id) throws Exception;
}
