package com.freezy.freezy_backend.Domain.Services;

import com.freezy.freezy_backend.Domain.RequestBodies.ItemBody;
import com.freezy.freezy_backend.Domain.RequestBodies.ItemReturnBody;
import com.freezy.freezy_backend.Persistence.Entities.*;
import com.freezy.freezy_backend.Persistence.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    public ItemService() {
    }

    public boolean createItem(ItemBody itemBody) {
        try {
            if (authenticationService.verifyToken(itemBody.getAccessToken())) {

                Storage_Unit storage_unit = storage_unit_repository.findStorage_UnitByIdWithItems(itemBody.getStorage_Unit_Id());

                if (storage_unit == null) {
                    storage_unit = storage_unit_repository.findStorage_UnitById(itemBody.getStorage_Unit_Id());
                }

                //Creating and adding item to selected storage_unit
                Item item = new Item(itemBody.getName(), itemBody.getExpirationDate(), itemBody.getUnit(), itemBody.getQuantity());

                //Adding categories to item
                for (Long categoryId : itemBody.getCategoryIds()) {
                    Category category = categoryRepository.findCategoryByIdWithItems(categoryId);

                    if (category == null) {
                        category = categoryRepository.findCategoryById(categoryId);
                    }

                    item.addCategoryToItem(category);
                }

                storage_unit.addItem(item);

                storage_unit_repository.save(storage_unit);

                return true;
            }
        } catch (Exception e) {
            System.out.println("CreateItem EXCEPTION: " + e);
        }
        return false;
    }

    public void updateItem(ItemBody itemBody) {
        try {
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
                if (itemBody.getQuantity() != null) {
                    item.setQuantity(itemBody.getQuantity());
                }
                if (itemBody.getStorage_Unit_Id() != null) {
                    //Removing item from old storage unit
                    Storage_Unit oldStorage_Unit = item.getStorage_unit();
                    oldStorage_Unit.removeItem(item);

                    //Adding item to new storage unit
                    Storage_Unit newStorage_Unit = storage_unit_repository.findStorage_UnitByIdWithItems(itemBody.getStorage_Unit_Id());
                    newStorage_Unit.addItem(item);

                    storage_unit_repository.save(oldStorage_Unit);
                    storage_unit_repository.save(newStorage_Unit);
                }
                if (itemBody.getCategoryIds() != null) {
                    item.getCategories().clear();
                    for (Long categoryID : itemBody.getCategoryIds()) {
                        Category category = categoryRepository.findCategoryByIdWithItems(categoryID);
                        if (!item.getCategories().contains(category)) {
                            item.addCategoryToItem(category);
                        }
                    }
                }

                itemRepository.save(item);
            }
        } catch (Exception e) {
            System.out.println("UpdateItem EXCEPTION: " + e);
        }
    }

    @Transactional
    public void deleteItem(ItemBody itemBody) {
        try {
            if (authenticationService.verifyToken(itemBody.getAccessToken())) {
                Token token = tokenRepository.getTokenByToken(itemBody.getAccessToken());
                Collection collection = collectionRepository.findCollectionById(token.getAccount_login().getAccount_details()
                        .getCollections().get(0).getId());

                Item item = itemRepository.findItemByIdWithCategories(itemBody.getItemId());

                //Removing all references to
                for (Category category : collection.getCategories()) {
                    Category category1 = categoryRepository.findCategoryByIdWithItems(category.getId());
                    category1.removeItemFromCategory(item);
                    categoryRepository.save(category1);
                }

                itemRepository.deleteById(itemBody.getItemId());
            }
        } catch (Exception e) {
            System.out.println("DeleteItem EXCEPTION: " + e);
        }
    }

    public List<ItemReturnBody> getAllItems(ItemBody itemBody) {
        try {
            List<ItemReturnBody> items = new ArrayList<>();

            Token token = tokenRepository.getTokenByToken(itemBody.getAccessToken());
            Collection collection = collectionRepository.findCollectionById(token.getAccount_login().getAccount_details()
                    .getCollections().get(0).getId());

            for (Storage_Unit storage_unit: collection.getStorage_units()) {
                for (Item item: storage_unit.getItems()) {
                    ItemReturnBody newItem = new ItemReturnBody(item.getId(), item.getName(), item.getExpiration_date(), item.getUnit(), item.getQuantity(), storage_unit.getId());
                    //Adding all category id's
                    List<Long> categoryIds = new ArrayList<>();
                    for (Category category : item.getCategories()) {
                        categoryIds.add(category.getId());
                    }
                    newItem.setCategoryIds(categoryIds);

                    items.add(newItem);
                }
            }

            return items;

        } catch (Exception e) {
            System.out.println("GetAllItemsException EXCEPTION: " + e);
        }
        return null;
    }
}
