package com.freezy.freezy_backend.Domain.Services;

import com.freezy.freezy_backend.Domain.RequestBodies.AccountDetails;
import com.freezy.freezy_backend.Domain.RequestBodies.Login;
import com.freezy.freezy_backend.Persistence.Entities.Account_Details;
import com.freezy.freezy_backend.Persistence.Entities.Account_Login;
import com.freezy.freezy_backend.Persistence.Entities.Collection;
import com.freezy.freezy_backend.Persistence.Entities.Token;
import com.freezy.freezy_backend.Persistence.Repositories.Account_Login_Repository;
import com.freezy.freezy_backend.Persistence.Repositories.CollectionRepository;
import com.freezy.freezy_backend.Persistence.Repositories.TokenRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthenticationService {

    @Autowired
    private Account_Login_Repository account_login_repository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private CollectionRepository collectionRepository;

    public AuthenticationService() {
    }

    //Authenticates the user login
    public boolean verifyLogin(Login login) {
        try {
            //Verify that username exists
            if (account_login_repository.existsByUsername(login.getUsername())) {
                Account_Login account_login = account_login_repository.findAccount_LoginByUsername(login.getUsername());
                //Verifying username and password.
                if (account_login.getUsername().equals(login.getUsername())
                        && BCrypt.checkpw(login.getPassword(), account_login.getPassword())) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("VerifyLogin EXCEPTION: " + e);
        }

        return false;
    }

    //Creates new user.
    public boolean createUser(Login login) {
        try {
            //Checking if username exists
            if (!account_login_repository.existsByUsername(login.getUsername())) {

                Account_Login account_login = new Account_Login(login.getUsername(),
                        BCrypt.hashpw(login.getPassword(), BCrypt.gensalt()));

                Account_Details account_details = new Account_Details(login.getAccountDetailsName());

                //Generating unique authenticationtoken
                UUID authenticationToken = UUID.randomUUID();
                while (tokenRepository.existsByToken(authenticationToken)) {
                    authenticationToken = UUID.randomUUID();
                }
                Token token = new Token(authenticationToken);

                //Generating unique collection token.
                UUID collectionToken = UUID.randomUUID();
                while (collectionRepository.countByCollection_token(collectionToken) > 0) {
                    collectionToken = UUID.randomUUID();
                }
                Collection collection = new Collection(collectionToken);

                account_details.addCollection(collection);

                account_login.addAccount_Details(account_details);

                account_login.addToken(token);

                account_login_repository.save(account_login);
            } else {
                return false;
            }

        } catch (Exception e) {
            System.out.println("CreateUser EXCEPTION: " + e);
            return false;
        }

        return true;
    }

    public boolean verifyToken(UUID token) {
        return tokenRepository.existsByToken(token);
    }

    public AccountDetails getAccountDetails(UUID authenticationToken) {
        try {
            Token token = tokenRepository.getTokenByToken(authenticationToken);

            AccountDetails accountDetails = new AccountDetails();
            accountDetails.setName(token.getAccount_login().getAccount_details().getName());
            accountDetails.setToken(token.getAccount_login().getAccount_details().getCollections().get(0).getCollection_token());

            return accountDetails;

        } catch (Exception e) {
            System.out.println("GetAccountDetails EXCEPTION: " + e);
        }
        return null;
    }
}
