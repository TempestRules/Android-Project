package com.freezy.freezy_backend.Domain.Services;

import com.freezy.freezy_backend.Domain.RequestBodies.CategoryBody;
import com.freezy.freezy_backend.Persistence.Entities.*;
import com.freezy.freezy_backend.Persistence.Repositories.CategoryRepository;
import com.freezy.freezy_backend.Persistence.Repositories.CollectionRepository;
import com.freezy.freezy_backend.Persistence.Repositories.ItemRepository;
import com.freezy.freezy_backend.Persistence.Repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private ItemRepository itemRepository;

    public CategoryService() {
    }

    public boolean createCategory(CategoryBody categoryBody) {
        try {
            if (authenticationService.verifyToken(categoryBody.getAccessToken())) {
                    Token token = tokenRepository.getTokenByToken(categoryBody.getAccessToken());
                    Collection collection = collectionRepository.findCollectionById(token.getAccount_login().getAccount_details()
                            .getCollections().get(0).getId());

                    if (collection.getCategories() == null) {
                        collection.setCategories(new ArrayList<>());
                    }

                    //Creating and adding the new category to the collection
                    Category category = new Category(categoryBody.getName(), categoryBody.getColor());
                    collection.addCategory(category);

                    collectionRepository.save(collection);
                    return true;
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

    @Transactional
    public void deleteCategory(CategoryBody categoryBody) {
        try {
            if (authenticationService.verifyToken(categoryBody.getAccessToken())) {

                Token token = tokenRepository.getTokenByToken(categoryBody.getAccessToken());
                Collection collection = collectionRepository.findCollectionById(token.getAccount_login().getAccount_details()
                        .getCollections().get(0).getId());

                Category category = categoryRepository.findCategoryByIdWithItems(categoryBody.getCategoryId());

                if (category != null) {

                    //Deleting items in this category
                    for (int i = category.getItems().size() - 1; i >= 0; i--) {
                        Item delItem = itemRepository.findItemByIdWithCategories(category.getItems().get(i).getId());
                        //Removing all references to category
                        delItem.removeCategoryFromItem(category);
                        itemRepository.deleteById(delItem.getId());
                    }

                } else {
                    category = categoryRepository.findCategoryById(categoryBody.getCategoryId());
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

    public List<Category> getAllCategories(CategoryBody categoryBody) {
        try {
            List<Category> categories = new ArrayList<>();

            Token token = tokenRepository.getTokenByToken(categoryBody.getAccessToken());
            Collection collection = collectionRepository.findCollectionById(token.getAccount_login().getAccount_details()
                    .getCollections().get(0).getId());

            for (Category category: collection.getCategories()) {
                Category newCategory = new Category(category.getName(), category.getColor());
                newCategory.setId(category.getId());

                categories.add(newCategory);
            }

            return categories;

        } catch (Exception e) {
            System.out.println("GetAllCategories EXCEPTION: " + e);
        }
        return null;
    }
}
