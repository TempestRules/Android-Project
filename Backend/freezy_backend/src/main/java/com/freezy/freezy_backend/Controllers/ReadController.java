package com.freezy.freezy_backend.Controllers;

import com.freezy.freezy_backend.Domain.RequestBodies.AccountDetails;
import com.freezy.freezy_backend.Domain.RequestBodies.AuthenticationToken;
import com.freezy.freezy_backend.Domain.RequestBodies.Login;
import com.freezy.freezy_backend.Domain.Services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/Read")
public class ReadController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/Authenticate")
    public ResponseEntity<AuthenticationToken> verifyLogin(@RequestBody Login login) {
        if (authenticationService.verifyLogin(login)) {
            //TODO: Create/Update AuthenticationToken and return it.
            AuthenticationToken authenticationToken = new AuthenticationToken();

            authenticationToken.setAuthenticationToken(UUID.randomUUID());

            return new ResponseEntity<>(authenticationToken, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
