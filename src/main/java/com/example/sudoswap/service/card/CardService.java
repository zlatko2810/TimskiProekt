package com.example.sudoswap.service.card;

import com.example.sudoswap.entity.nft.CardEntity;
import com.example.sudoswap.entity.user.UserEntity;
import com.example.sudoswap.entity.wallet.WalletType;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CardService {

    public void addCard(String name, String description, float price, MultipartFile multipartFile, Long collectionId) throws Exception;

    public void editCard(Long cardId, String name, String description, float price, MultipartFile multipartFile, Long collectionId) throws Exception;

    public void deleteCard(Long cardId) throws Exception;

    public void buyCard(WalletType walletType, Long cardId) throws Exception;

    public List<CardEntity> findAllAdmin() throws Exception;

    public Page<CardEntity> findAllPagination(int page, int pageSize, String name, Long collectionId, boolean userFlag) throws Exception;

    public CardEntity findById(Long id) throws Exception;

    public List<CardEntity> getOneCardFromEachCollection();

}
