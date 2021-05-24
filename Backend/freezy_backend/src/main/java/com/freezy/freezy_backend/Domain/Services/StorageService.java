package com.freezy.freezy_backend.Domain.Services;

import com.freezy.freezy_backend.Domain.RequestBodies.Storage;
import com.freezy.freezy_backend.Persistence.Entities.Collection;
import com.freezy.freezy_backend.Persistence.Entities.Storage_Unit;
import com.freezy.freezy_backend.Persistence.Entities.Token;
import com.freezy.freezy_backend.Persistence.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public boolean createStorage_Unit(Storage storage) {
        try {
            if (authenticationService.verifyToken(storage.getAccessToken())) {
                    Token token = tokenRepository.getTokenByToken(storage.getAccessToken());
                    Collection collection = collectionRepository.findCollectionById(token.getAccount_login().getAccount_details()
                            .getCollections().get(0).getId());

                    if (collection.getStorage_units() == null) {
                        collection.setStorage_units(new ArrayList<>());
                    }

                    //Creating and adding the new Storage_Unit to the collection.
                    Storage_Unit storage_unit = new Storage_Unit(storage.getName(), storage.getColor());
                    collection.addStorage_Unit(storage_unit);

                    collectionRepository.save(collection);
                    return true;
            }
        } catch (Exception e) {
            System.out.println("CreateStorage_Unit EXCEPTION: " + e);
        }

        return false;
    }

    public void updateStorage_Unit(Storage storage) {
        try {
            if (authenticationService.verifyToken(storage.getAccessToken())) {
                Storage_Unit storage_unit = storage_unit_repository.findStorage_UnitById(storage.getStorageId());
                storage_unit.setName(storage.getName());
                storage_unit.setColor(storage.getColor());
                storage_unit_repository.save(storage_unit);
            }
        } catch (Exception e) {
            System.out.println("UpdateStorage_Unit EXCEPTION: " + e);
        }
    }

    public void deleteStorage_Unit(Storage storage) {
        try {
            if (authenticationService.verifyToken(storage.getAccessToken())) {
                storage_unit_repository.deleteById(storage.getStorageId());
            }
        } catch (Exception e) {
            System.out.println("DeleteStorage_Unit EXCEPTION: " + e);
        }
    }

    public List<Storage_Unit> getAllStorages(Storage storage) {
        try {
            List<Storage_Unit> storage_units = new ArrayList<>();

            Token token = tokenRepository.getTokenByTokenWithAccountLogin(storage.getAccessToken());
            Collection collection = collectionRepository.findCollectionById(token.getAccount_login().getAccount_details()
                    .getCollections().get(0).getId());

            for (Storage_Unit storage_unit: collection.getStorage_units()) {
                Storage_Unit newStorageUnit = new Storage_Unit(storage_unit.getName(), storage_unit.getColor());
                newStorageUnit.setId(storage_unit.getId());

                storage_units.add(newStorageUnit);
            }

            return storage_units;

        } catch (Exception e) {
            System.out.println("GetAllStorages EXCEPTION: " + e);
        }
        return null;
    }
}
