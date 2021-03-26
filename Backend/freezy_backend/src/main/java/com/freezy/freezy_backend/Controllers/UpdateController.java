package com.freezy.freezy_backend.Controllers;

import com.freezy.freezy_backend.Domain.RequestBodies.CategoryBody;
import com.freezy.freezy_backend.Domain.RequestBodies.Storage;
import com.freezy.freezy_backend.Domain.Services.CategoryService;
import com.freezy.freezy_backend.Domain.Services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Update")
public class UpdateController {

    @Autowired
    private StorageService storageService;

    @Autowired
    private CategoryService categoryService;

    @PutMapping("/Storage")
    public ResponseEntity<?> updateStorage_unit(@RequestBody Storage storage) {
        storageService.updateStorage_Unit(storage);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/Category")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryBody categoryBody) {
        categoryService.updateCategory(categoryBody);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
