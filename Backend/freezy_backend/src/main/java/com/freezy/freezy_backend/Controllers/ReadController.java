package com.freezy.freezy_backend.Controllers;

import com.freezy.freezy_backend.Domain.RequestBodies.*;
import com.freezy.freezy_backend.Domain.Services.AuthenticationService;
import com.freezy.freezy_backend.Domain.Services.CategoryService;
import com.freezy.freezy_backend.Domain.Services.ItemService;
import com.freezy.freezy_backend.Domain.Services.StorageService;
import com.freezy.freezy_backend.Persistence.Entities.Account_Login;
import com.freezy.freezy_backend.Persistence.Entities.Category;
import com.freezy.freezy_backend.Persistence.Entities.Storage_Unit;
import com.freezy.freezy_backend.Persistence.Repositories.Account_Login_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Read")
public class ReadController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private Account_Login_Repository account_login_repository;

    @Autowired
    private ItemService itemService;

    @PostMapping("/Authenticate")
    public ResponseEntity<AuthenticationToken> verifyLogin(@RequestBody Login login) {
        if (authenticationService.verifyLogin(login)) {
            Account_Login accountLogin = account_login_repository.findAccount_LoginByUsername(login.getUsername());

            AuthenticationToken authenticationToken = new AuthenticationToken();
            authenticationToken.setAuthenticationToken(accountLogin.getToken().getToken());

            return new ResponseEntity<>(authenticationToken, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/AccountDetails")
    public ResponseEntity<AccountDetails> getAccountDetails(@RequestBody AuthenticationToken authenticationToken) {
        if (authenticationService.verifyToken(authenticationToken.getAuthenticationToken())) {
            AccountDetails accountDetails = authenticationService.getAccountDetails(authenticationToken.getAuthenticationToken());
            return new ResponseEntity<>(accountDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/AllStorages")
    public ResponseEntity<List<Storage_Unit>> getAllStorage_Units(@RequestBody Storage storage) {
        if (authenticationService.verifyToken(storage.getAccessToken())) {
            List<Storage_Unit> storage_units = storageService.getAllStorages(storage);
            return new ResponseEntity<>(storage_units, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/AllCategories")
    public ResponseEntity<List<Category>> getAllCategories(@RequestBody CategoryBody categoryBody) {
        if (authenticationService.verifyToken(categoryBody.getAccessToken())) {
            List<Category> categories = categoryService.getAllCategories(categoryBody);
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/Items")
    public ResponseEntity<List<ItemReturnBody>> getAllItems(@RequestBody ItemBody itemBody) {
        if (authenticationService.verifyToken(itemBody.getAccessToken())) {
            List<ItemReturnBody> items = itemService.getAllItems(itemBody);
            return new ResponseEntity<>(items, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
