package com.freezy.freezy_backend.Controllers;

import com.freezy.freezy_backend.Domain.RequestBodies.CategoryBody;
import com.freezy.freezy_backend.Domain.RequestBodies.ItemBody;
import com.freezy.freezy_backend.Domain.RequestBodies.Storage;
import com.freezy.freezy_backend.Domain.Services.CategoryService;
import com.freezy.freezy_backend.Domain.Services.ItemService;
import com.freezy.freezy_backend.Domain.Services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Delete")
public class DeleteController {

    @Autowired
    private StorageService storageService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ItemService itemService;

    @DeleteMapping("/Storage")
    public ResponseEntity<?> deleteStorage_Unit(@RequestBody Storage storage) {
        storageService.deleteStorage_Unit(storage);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/Category")
    public ResponseEntity<?> deleteCategory(@RequestBody CategoryBody categoryBody) {
        categoryService.deleteCategory(categoryBody);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/Item")
    public ResponseEntity<?> deleteItem(@RequestBody ItemBody itemBody) {
        itemService.deleteItem(itemBody);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
