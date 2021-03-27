package com.freezy.freezy_backend.Domain.Services;

import com.freezy.freezy_backend.Domain.RequestBodies.ItemBody;
import com.freezy.freezy_backend.Persistence.Entities.*;
import com.freezy.freezy_backend.Persistence.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    Storage_Unit_Repository storage_unit_repository;

    @Autowired
    CollectionRepository collectionRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ItemRepository itemRepository;

    public boolean createItem(ItemBody itemBody) {
        if (authenticationService.verifyToken(itemBody.getAccessToken())) {

            Storage_Unit storage_unit = storage_unit_repository.findStorage_UnitByWithItems(itemBody.getStorage_Unit_Id());

            //Creating and adding item to selected storage_unit
            Item item = new Item(itemBody.getName(), itemBody.getExpirationDate(), itemBody.getUnit());

            //Adding categories to item
            for (Long categoryId: itemBody.getCategoryIds()) {
                Category category = categoryRepository.findCategoryByIdWithItems(categoryId);

                item.addCategoryToItem(category);
            }

            storage_unit.addItem(item);

            storage_unit_repository.save(storage_unit);

            return true;
        } else {
            return false;
        }
    }

    public void updateItem(ItemBody itemBody) {
        if (authenticationService.verifyToken(itemBody.getAccessToken())) {

            Item item = itemRepository.findItemByIdWithStorage_Unit(itemBody.getItemId());

            if (itemBody.getName() != null) {
                item.setName(itemBody.getName());
            }
            if (itemBody.getExpirationDate() != null) {
                item.setExpiration_date(itemBody.getExpirationDate());
            }
            if (itemBody.getUnit() != null) {
                item.setUnit(itemBody.getUnit());
            }
            if (itemBody.getStorage_Unit_Id() != null) {
                //Removing item from old storage unit
                Storage_Unit oldStorage_Unit = item.getStorage_unit();
                oldStorage_Unit.removeItem(item);

                //Adding item to new storage unit
                Storage_Unit newStorage_Unit = storage_unit_repository.findStorage_UnitByWithItems(itemBody.getStorage_Unit_Id());
                newStorage_Unit.addItem(item);

                storage_unit_repository.save(oldStorage_Unit);
                storage_unit_repository.save(newStorage_Unit);
            }
            if (itemBody.getCategoryIds() != null) {
                for (Long categoryID: itemBody.getCategoryIds()) {
                    Category category = categoryRepository.findCategoryByIdWithItems(categoryID);
                    item.addCategoryToItem(category);
                }
            }

            itemRepository.save(item);

        }
    }

    //TODO: Don't know why, but this works. Might break idk.
    public void deleteItem(ItemBody itemBody) {
        if (authenticationService.verifyToken(itemBody.getAccessToken())) {
            Token token = tokenRepository.getTokenByToken(itemBody.getAccessToken());
            Collection collection = collectionRepository.findCollectionById(token.getAccount_login().getAccount_details()
                    .getCollections().get(0).getId());

            Item item = itemRepository.findItemByIdWithCategories(itemBody.getItemId());

            //Removing all references to
            for (Category category: collection.getCategories()) {
                Category category1 = categoryRepository.findCategoryByIdWithItems(category.getId());
                category1.removeItemFromCategory(item);
            }

            itemRepository.deleteById(itemBody.getItemId());
        }
    }
}
