package com.freezy.freezy_backend.Controllers;


import com.freezy.freezy_backend.Domain.RequestBodies.Login;
import com.freezy.freezy_backend.Domain.RequestBodies.Storage;
import com.freezy.freezy_backend.Domain.Services.AuthenticationService;
import com.freezy.freezy_backend.Domain.Services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Create")
public class CreateController {

    @Autowired
    private AuthenticationService authenticationService;

    //private final StorageService storageService;

    //Signing a new user up.
    @PostMapping("/Signup")
    public ResponseEntity<?> signup(@RequestBody Login login) {
        if (authenticationService.createUser(login)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //TODO
    @PostMapping("/Storage")
    public ResponseEntity<?> addStorage(@RequestBody Storage storage) {
        if (authenticationService.verifyToken(storage.getAccessToken())) {


            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
