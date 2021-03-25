package com.freezy.freezy_backend.Controllers;

import com.freezy.freezy_backend.Domain.RequestBodies.AccountDetails;
import com.freezy.freezy_backend.Domain.RequestBodies.AuthenticationToken;
import com.freezy.freezy_backend.Domain.Services.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Read")
public class ReadController {

    private final AuthenticationService authenticationService;

    public ReadController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/AccountDetails")
    public ResponseEntity<AccountDetails> getAccountDetails(@RequestBody AuthenticationToken authenticationToken) {
        if (authenticationService.verifyToken(authenticationToken.getAuthenticationToken())) {
            AccountDetails accountDetails = authenticationService.getAccountDetails(authenticationToken.getAuthenticationToken());
            return new ResponseEntity<>(accountDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
