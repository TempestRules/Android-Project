package com.freezy.freezy_backend.Domain.Services;

import com.freezy.freezy_backend.Domain.RequestBodies.Storage;
import com.freezy.freezy_backend.Persistence.Entities.Collection;
import com.freezy.freezy_backend.Persistence.Entities.Storage_Unit;
import com.freezy.freezy_backend.Persistence.Entities.Token;
import com.freezy.freezy_backend.Persistence.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private Storage_Unit_Repository storage_unit_repository;

    @Autowired
    private AuthenticationService authenticationService;


    public StorageService() {
    }

    public boolean addStorage_Unit(Storage storage) {
        if (authenticationService.verifyToken(storage.getAccessToken())) {
            //Making sure that two identical storage_units can't be created.
            if (!storage_unit_repository.existsByName(storage.getName())) {
                Token token = tokenRepository.getTokenByToken(storage.getAccessToken());
                Collection collection = collectionRepository.findCollectionById(token.getAccount_login().getAccount_details()
                        .getCollections().get(0).getId());

                //Creating and adding the new Storage_Unit to the collection.
                Storage_Unit storage_unit = new Storage_Unit(storage.getName());
                collection.addStorage_Unit(storage_unit);

                collectionRepository.save(collection);
                return true;
            }

        } else {
            return false;
        }

        return false;
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
