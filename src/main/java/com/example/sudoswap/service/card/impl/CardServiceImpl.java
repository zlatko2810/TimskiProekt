package com.example.sudoswap.service.card.impl;

import com.example.sudoswap.entity.nft.CardEntity;
import com.example.sudoswap.entity.nft.CollectionEntity;
import com.example.sudoswap.entity.transaction.NFTTransactionEntity;
import com.example.sudoswap.entity.transaction.TransactionType;
import com.example.sudoswap.entity.user.UserEntity;
import com.example.sudoswap.entity.wallet.WalletEntity;
import com.example.sudoswap.entity.wallet.WalletType;
import com.example.sudoswap.repository.CardRepository;
import com.example.sudoswap.repository.CollectionRepository;
import com.example.sudoswap.repository.NFTTransactionRepository;
import com.example.sudoswap.repository.WalletRepository;
import com.example.sudoswap.service.card.CardService;
import com.example.sudoswap.service.common.CommonService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final CollectionRepository collectionRepository;
    private final NFTTransactionRepository nftTransactionRepository;
    private final WalletRepository walletRepository;
    private final CommonService commonService;

    private final static float BTC_PRICE = 25753.0F;
    private final static float ETH_PRICE = 1637.58F;

    public CardServiceImpl(CardRepository cardRepository, CollectionRepository collectionRepository, NFTTransactionRepository nftTransactionRepository, WalletRepository walletRepository, CommonService commonService) {
        this.cardRepository = cardRepository;
        this.collectionRepository = collectionRepository;
        this.nftTransactionRepository = nftTransactionRepository;
        this.walletRepository = walletRepository;
        this.commonService = commonService;
    }

    @Override
    public void addCard(String name, String description, float price, MultipartFile multipartFile, Long collectionId) throws Exception {
        CollectionEntity collection = collectionRepository.findById(collectionId).orElseThrow(Exception::new);
        if (collection.getCards().size() + 1 <= collection.getNumberOfCards()) {
            CardEntity card = new CardEntity(name, description, price, multipartFile.getBytes());
            card.setCollection(collection);
            card.setUser(commonService.getLoggedInUser());
            card.setSellFlag(true);
            cardRepository.save(card);
            collection.getCards().add(card);
            collectionRepository.save(collection);
        } else {
            throw new Exception("You have exceed the limit of the collection");
        }

    }

    @Transactional
    @Override
    public void editCard(Long cardId, String name, String description, float price, MultipartFile multipartFile, Long collectionId) throws Exception {
        CardEntity card = cardRepository.findById(cardId).orElseThrow(Exception::new);
        CollectionEntity collection = collectionRepository.findById(collectionId).orElseThrow(Exception::new);
        card.setName(name);
        card.setPrice(price);
        card.setDescription(description);

        if (!multipartFile.isEmpty()) {
            card.setImage(multipartFile.getBytes());
        }

        if (!Objects.equals(card.getCollection().getId(), collectionId)) {
            if (collection.getCards().size() + 1 <= collection.getNumberOfCards()) {
                CollectionEntity oldCollection = collectionRepository.findByCardsContaining(card).get();
                oldCollection.getCards().remove(card);

                card.setCollection(collection);
                cardRepository.save(card);

                collectionRepository.save(oldCollection);

                collection.getCards().add(card);
                collectionRepository.save(collection);
            } else {
                throw new Exception("You have exceed the limit of the collection");
            }

        } else {
            cardRepository.save(card);
        }


    }

    @Override
    public void deleteCard(Long cardId) throws Exception {
        CardEntity card = cardRepository.findById(cardId).orElseThrow(Exception::new);
        CollectionEntity collection = collectionRepository.findByCardsContaining(card).orElseThrow(Exception::new);
        collection.getCards().remove(card);
        collectionRepository.save(collection);
        cardRepository.delete(card);
    }

    @Override
    public void buyCard(WalletType walletType, Long cardId) throws Exception {
        UserEntity user = commonService.getLoggedInUser();
        CardEntity card = cardRepository.findById(cardId).orElseThrow(Exception::new);
        if(card.getUser().getId() != user.getId()) {
            for (WalletEntity wallet : user.getWallets()) {
                if (wallet.getWalletType().equals(walletType) && wallet.getWalletType().equals(WalletType.BTC)) {
                    float walletMoney = wallet.getMoney();
                    float cardMoney = card.getPrice() / BTC_PRICE;
                    if (walletMoney >= cardMoney) {
                        wallet.setMoney(wallet.getMoney() - cardMoney);
                        walletRepository.save(wallet);
                        transferMoney(WalletType.BTC, cardMoney, card.getUser());
                        addNFTTransaction(card.getPrice(), card, card.getUser());
                        transferOwnership(card, user);
                        break;
                    }
                }

                if (wallet.getWalletType().equals(walletType) && wallet.getWalletType().equals(WalletType.ETH)) {
                    float walletMoney = wallet.getMoney();
                    float cardMoney = card.getPrice() / ETH_PRICE;
                    if (walletMoney >= cardMoney) {
                        wallet.setMoney(wallet.getMoney() - cardMoney);
                        walletRepository.save(wallet);
                        transferMoney(WalletType.ETH, cardMoney, card.getUser());
                        addNFTTransaction(card.getPrice(), card, card.getUser());
                        transferOwnership(card, user);
                        break;
                    }
                }
            }
        }


    }

    @Override
    public List<CardEntity> findAllAdmin() throws Exception {
        return cardRepository.findAllByUser(commonService.getAdminUser());
    }

    @Override
    public Page<CardEntity> findAllPagination(int page, int pageSize, String name, Long collectionId, boolean userFlag) throws Exception {
        PageRequest pageable = PageRequest.of(page, pageSize, Sort.by("id").ascending());
        if (!userFlag) {
            if (name != null) {
                return cardRepository.findAllByNameContainingAndSellFlag(name, true, pageable);
            }
            if (collectionId != null) {
                return cardRepository.findAllByCollectionAndSellFlag(collectionRepository.findById(collectionId).orElseThrow(Exception::new), true, pageable);
            }
            return cardRepository.findAllBySellFlag(true, pageable);
        } else {
            if (name != null) {
                return cardRepository.findAllByNameContainingAndSellFlagAndUser(name, true, commonService.getLoggedInUser(), pageable);
            }
            if (collectionId != null) {
                return cardRepository.findAllByCollectionAndSellFlagAndUser(collectionRepository.findById(collectionId).orElseThrow(Exception::new), true, commonService.getLoggedInUser(), pageable);
            }
            return cardRepository.findAllByUser(commonService.getLoggedInUser(), pageable);
        }


    }

    @Override
    public CardEntity findById(Long id) throws Exception {
        return cardRepository.findById(id).orElseThrow(Exception::new);
    }

    @Override
    public List<CardEntity> getOneCardFromEachCollection() {
        return cardRepository.getOneCardFromEachCollection();
    }


    public void transferMoney(WalletType walletType, float money, UserEntity owner) throws Exception {
        for (WalletEntity wallet : owner.getWallets()) {
            if (wallet.getWalletType().equals(walletType)) {
                wallet.setMoney(wallet.getMoney() + (money - calculateFee(money)));
                walletRepository.save(wallet);
                break;
            }
        }
        transferFee(walletType, money);
    }

    public void transferOwnership(CardEntity card, UserEntity user) {
        card.setUser(user);
        card.setSellFlag(false);
        cardRepository.save(card);
    }

    public float calculateFee(float amount) {
        return (amount * 2.5F) / 100.0F;
    }

    public void transferFee(WalletType walletType, float amount) throws Exception {
        for (WalletEntity wallet : commonService.getAdminUser().getWallets()) {
            if (wallet.getWalletType().equals(walletType)) {
                wallet.setMoney(wallet.getMoney() + calculateFee(amount));
                walletRepository.save(wallet);
                break;
            }
        }
    }

    public void addNFTTransaction(float amount, CardEntity card, UserEntity oldUser) throws Exception {
        nftTransactionRepository.save(new NFTTransactionEntity(amount, TransactionType.BUY, card, commonService.getLoggedInUser()));
        nftTransactionRepository.save(new NFTTransactionEntity(amount, TransactionType.SELL, card, oldUser));
    }
}
