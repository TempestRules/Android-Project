package com.freezy.freezy_backend.Domain.Services;

import com.freezy.freezy_backend.Domain.RequestBodies.CategoryBody;
import com.freezy.freezy_backend.Persistence.Entities.Category;
import com.freezy.freezy_backend.Persistence.Entities.Item;
import com.freezy.freezy_backend.Persistence.Entities.Token;
import com.freezy.freezy_backend.Persistence.Repositories.CategoryRepository;
import com.freezy.freezy_backend.Persistence.Repositories.CollectionRepository;
import com.freezy.freezy_backend.Persistence.Repositories.ItemRepository;
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

    @Autowired
    private ItemRepository itemRepository;


    //TODO: In progress
    public void createCategory(CategoryBody categoryBody) {

        Token token = tokenRepository.getTokenByToken(categoryBody.getAccessToken());

        //Collection collection = collectionRepository.findCollectionById();

    }

    public void updateCategory(CategoryBody categoryBody) {
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
    }

    public void deleteCategory(CategoryBody categoryBody) {
        if (authenticationService.verifyToken(categoryBody.getAccessToken())) {

            Token token = tokenRepository.getTokenByToken(categoryBody.getAccessToken());

            //TODO: Delete every reference to Category


            categoryRepository.deleteById(categoryBody.getCategoryId());
        }
    }
}
