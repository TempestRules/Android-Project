package com.freezy.freezy_backend;

import com.freezy.freezy_backend.Persistence.Entities.*;
import com.freezy.freezy_backend.Persistence.Repositories.Account_Login_Repository;
import com.google.gson.Gson;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.UUID;

@SpringBootApplication
public class FreezyBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreezyBackendApplication.class, args);
	}

	private Gson gson = new Gson();

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		//corsConfiguration.setAllowCredentials(true);
		//Allowing all origins
		corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Authorization", "Origin, Accept", "X-Requested-With",
				"Access-Control-Request-Method", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}

	//Test method
	@Bean
	CommandLineRunner commandLineRunner(Account_Login_Repository account_login_repository) {
		return args -> {

			createBasicAccount(account_login_repository);
		};
	}

	private void createBasicAccount(Account_Login_Repository account_login_repository) {

		UUID accessToken = UUID.randomUUID();

		Account_Login account_login = new Account_Login("TestUser", BCrypt.hashpw("Password", BCrypt.gensalt()));

		Token token = new Token(accessToken);

		Account_Details account_details = new Account_Details("TestUserName");

		Collection collection = new Collection(UUID.randomUUID());

		Storage_Unit storage_unit = new Storage_Unit("Freezer", "Blue");

		Item item = new Item("Oksekd", "2021-04-24T00:00:00", "500 grams", 1);

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
