package com.freezy.freezy_backend;

import com.freezy.freezy_backend.Domain.RequestBodies.ItemBody;
import com.freezy.freezy_backend.Domain.Services.ItemService;
import com.freezy.freezy_backend.Persistence.Entities.*;
import com.freezy.freezy_backend.Persistence.Repositories.Account_Details_Repository;
import com.freezy.freezy_backend.Persistence.Repositories.Account_Login_Repository;
import com.freezy.freezy_backend.Persistence.Repositories.TokenRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class FreezyBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreezyBackendApplication.class, args);
	}

	private Gson gson = new Gson();

	//Test method
	@Bean
	CommandLineRunner commandLineRunner(Account_Login_Repository account_login_repository, TokenRepository tokenRepository, Account_Details_Repository account_details_repository) {
		return args -> {

			createBasicAccount(account_login_repository);


		};
	}

	private void createBasicAccount(Account_Login_Repository account_login_repository) {

		BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();

		UUID accessToken = UUID.randomUUID();
		LocalDateTime localDateTime = LocalDateTime.now(Clock.systemDefaultZone());

		Account_Login account_login = new Account_Login("TestUser", bCrypt.encode("password"));

		Token token = new Token(accessToken, localDateTime);

		Account_Details account_details = new Account_Details("TestUserName");

		Collection collection = new Collection(UUID.randomUUID());

		Storage_Unit storage_unit = new Storage_Unit("Freezer");

		Item item = new Item("Oksekød", localDateTime, "500 grams");

		Category category = new Category("Meat", "Red");

		//Putting it all together.
		item.addCategoryToItem(category);

		storage_unit.addItem(item);

		collection.addStorage_Unit(storage_unit);

		collection.addCategory(category);

		account_details.addCollection(collection);

		account_login.addToken(token);
		account_login.addAccount_Details(account_details);

		//Saving
		account_login_repository.save(account_login);
	}

}
