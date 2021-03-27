package com.freezy.freezy_backend.Domain.Services;

import com.freezy.freezy_backend.Domain.RequestBodies.CategoryBody;
import com.freezy.freezy_backend.Persistence.Entities.*;
import com.freezy.freezy_backend.Persistence.Repositories.CategoryRepository;
import com.freezy.freezy_backend.Persistence.Repositories.CollectionRepository;
import com.freezy.freezy_backend.Persistence.Repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public boolean createCategory(CategoryBody categoryBody) {
        try {
            if (authenticationService.verifyToken(categoryBody.getAccessToken())) {
                //Making sure that two identical categories can't be created.
                if (!categoryRepository.existsByName(categoryBody.getName())) {
                    Token token = tokenRepository.getTokenByToken(categoryBody.getAccessToken());
                    Collection collection = collectionRepository.findCollectionById(token.getAccount_login().getAccount_details()
                            .getCollections().get(0).getId());

                    //Creating and adding the new category to the collection
                    Category category = new Category(categoryBody.getName(), categoryBody.getColor());
                    collection.addCategory(category);

                    collectionRepository.save(collection);
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("CreateCategory EXCEPTION: " + e);
        }
        //Returns false if token can't be verified.
        return false;
    }

    public void updateCategory(CategoryBody categoryBody) {
        try {
            if (authenticationService.verifyToken(categoryBody.getAccessToken())) {
                Category category = categoryRepository.findCategoryById(categoryBody.getCategoryId());

                if (categoryBody.getName() != null) {
                    category.setName(categoryBody.getName());
                }
                if (categoryBody.getColor() != null) {
                    category.setColor(categoryBody.getColor());
                }

                categoryRepository.save(category);
            }
        } catch (Exception e) {
            System.out.println("UpdateCategory EXCEPTION: " + e);
        }
    }

    public void deleteCategory(CategoryBody categoryBody) {
        try {
            if (authenticationService.verifyToken(categoryBody.getAccessToken())) {

                Token token = tokenRepository.getTokenByToken(categoryBody.getAccessToken());
                Collection collection = collectionRepository.findCollectionById(token.getAccount_login().getAccount_details()
                        .getCollections().get(0).getId());

                Category category = categoryRepository.findCategoryById(categoryBody.getCategoryId());

                //Deleting every reference to every item in every storage unit
                for (Storage_Unit storage_unit : collection.getStorage_units()) {
                    for (Item item : storage_unit.getItems()) {
                        item.removeCategoryFromItem(category);
                    }
                }
                //Deleting every reference to Collection
                collection.removeCategory(category);

                //Now finally deleting the category
                categoryRepository.deleteById(categoryBody.getCategoryId());
            }
        } catch (Exception e) {
            System.out.println("DeleteCategory EXCEPTION: " + e);
        }
    }
}
