package com.freezy.freezy_backend.Domain.Services;

import com.freezy.freezy_backend.Domain.RequestBodies.AuthenticationToken;
import com.freezy.freezy_backend.Domain.RequestBodies.Storage;
import com.freezy.freezy_backend.Persistence.Entities.Storage_Unit;
import com.freezy.freezy_backend.Persistence.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private Account_Login_Repository account_login_repository;

    @Autowired
    private Account_Details_Repository account_details_repository;

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private Storage_Unit_Repository storage_unit_repository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AuthenticationService authenticationService;


    public StorageService() {
    }

    public void addStorage_Unit(Storage storage) {

    }

    public void updateStorage_Unit(Storage storage) {
        if (authenticationService.verifyToken(storage.getAccessToken())) {
            Storage_Unit storage_unit = storage_unit_repository.findStorage_UnitById(storage.getStorageId());
            storage_unit.setName(storage.getName());
            storage_unit_repository.save(storage_unit);
        }
        //TODO: No else?
    }

    public void deleteStorage_Unit(Storage storage) {
        if (authenticationService.verifyToken(storage.getAccessToken())) {
            storage_unit_repository.deleteById(storage.getStorageId());
        }
    }


}
