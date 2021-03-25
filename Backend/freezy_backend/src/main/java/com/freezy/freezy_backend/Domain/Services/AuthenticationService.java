package com.freezy.freezy_backend.Domain.Services;

import com.freezy.freezy_backend.Domain.RequestBodies.AccountDetails;
import com.freezy.freezy_backend.Persistence.Entities.Token;
import com.freezy.freezy_backend.Persistence.Repositories.Account_Login_Repository;
import com.freezy.freezy_backend.Persistence.Repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthenticationService {

    private final Account_Login_Repository account_login_repository;

    private final TokenRepository tokenRepository;

    @Autowired
    public AuthenticationService(Account_Login_Repository account_login_repository, TokenRepository tokenRepository) {
        this.account_login_repository = account_login_repository;
        this.tokenRepository = tokenRepository;
    }

    //TODO
    //Checks if token exists. If it does, but is expired a new one is created.
    public boolean verifyToken(UUID token) {
        return tokenRepository.existsByToken(token);
    }

    public AccountDetails getAccountDetails(UUID authenticationToken) {
        Token token = tokenRepository.getTokenByToken(authenticationToken);

        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setName(token.getAccount_login().getAccount_details().getName());
        accountDetails.setToken(token.getAccount_login().getAccount_details().getCollections().get(0).getCollection_token());

        return accountDetails;
    }
}
