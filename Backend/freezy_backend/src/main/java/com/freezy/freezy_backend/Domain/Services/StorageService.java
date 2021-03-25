package com.freezy.freezy_backend.Domain.Services;

import com.freezy.freezy_backend.Domain.RequestBodies.Storage;
import com.freezy.freezy_backend.Persistence.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageService {

    private final TokenRepository tokenRepository;

    private final Account_Login_Repository account_login_repository;

    private final Account_Details_Repository account_details_repository;

    private final CollectionRepository collectionRepository;

    private final Storage_Unit_Repository storage_unit_repository;

    private final ItemRepository itemRepository;

    private final CategoryRepository categoryRepository;

    @Autowired
    public StorageService(TokenRepository tokenRepository, Account_Login_Repository account_login_repository, Account_Details_Repository account_details_repository, CollectionRepository collectionRepository, Storage_Unit_Repository storage_unit_repository, ItemRepository itemRepository, CategoryRepository categoryRepository) {
        this.tokenRepository = tokenRepository;
        this.account_login_repository = account_login_repository;
        this.account_details_repository = account_details_repository;
        this.collectionRepository = collectionRepository;
        this.storage_unit_repository = storage_unit_repository;
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }

    public void addStorage_Unit(Storage storage) {

    }
}
