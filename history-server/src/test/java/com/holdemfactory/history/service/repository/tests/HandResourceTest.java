package com.holdemfactory.history.service.repository.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.holdemfactory.history.service.enums.PokerSite;
import com.holdemfactory.history.service.repository.Hand;
import com.holdemfactory.history.service.repository.Hero;
import com.holdemfactory.history.service.repository.Player;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
@TestMethodOrder(OrderAnnotation.class)
public class HandResourceTest {

	@Test
	@Order(1)
	void testRetrieveAll() {
		Response result =
				given()
				.when().get("/hands")
				.then()
				.statusCode(200)
				.body(
						containsString("Chimaera V"),
						containsString("Chimaera V"),
						containsString("Clematis")
						)
				.extract()
				.response();

		List<Hand> hands = result.jsonPath().getList("$");
		assertThat(hands, not(empty()));
		assertThat(hands, hasSize(3));
	}

	@Test
	@Order(2)
	void testGetHand() {
		Hand hand =
				given()
				.when().get("/hands/{handNumber}", 223914234478L)
				.then()
				.statusCode(200)
				.extract()
				.as(Hand.class);

		assertThat(hand.getHandNumber(), equalTo(223914234478L));
		assertThat(hand.getTableName(), equalTo("Chimaera V"));
		
		assertThat(hand.getPlayers(), hasSize(4));
		assertThat(hand.getHero(), notNullValue());
		
		Player yvel310 = hand.getPlayers().stream().filter(p -> p.getPlayerName().equals("yvel310")).findFirst().get();
		assertThat(yvel310.getHero(), notNullValue()); 
	}

	@Test
	@Order(3)
	void testCreateHand() throws Exception {
		Hand newHand = new Hand();
		newHand.setHandNumber(324324L);
		newHand.setTableName("Test");
		
		Player newPlayer1 = new Player();
		newPlayer1.setPlayerName("yvel310");
		newPlayer1.setSite(PokerSite.POKERSTARS);
		newHand.addToPlayers(newPlayer1);
		
		Player newPlayer2 = new Player();
		newPlayer2.setPlayerName("makkrik");
		newPlayer2.setSite(PokerSite.POKERSTARS);
		newHand.addToPlayers(newPlayer2);

		Hero hero = new Hero();
		hero.setPlayer(newPlayer1);
		hero.setHand(newHand);
		
		Hand returnedHand =
				given()
				.contentType(ContentType.JSON)
				.body(newHand).log().all()
				.when().post("/hands")
				.then()
				.statusCode(201)
				.extract()
				.as(Hand.class);

		assertThat(returnedHand, notNullValue());
		newHand.setHandId(returnedHand.getHandId());
		
		assertThat(returnedHand, equalTo(newHand));
		assertThat(returnedHand.getPlayers(), hasSize(2));
		assertThat(returnedHand.getHero(), notNullValue());
		
		Player player = returnedHand.getPlayers().stream().filter(p -> p.getPlayerName().equals("yvel310")).findFirst().get();
		assertThat(player.getHero(), notNullValue());
		
		Response result =
				given()
				.when().get("/hands")
				.then()
				.statusCode(200)
				.body(
						containsString("Chimaera V"),
						containsString("Chimaera V"),
						containsString("Clematis"),
						containsString("Test")
						)
				.extract()
				.response();

		List<Hand> accounts = result.jsonPath().getList("$");
		assertThat(accounts, not(empty()));
		assertThat(accounts, hasSize(4));
	}

	//	@Test
	//	@Order(4)
	//	void testCloseAccount() {
	//		given()
	//		.when().delete("/accounts/{accountNumber}", 5465)
	//		.then()
	//		.statusCode(204);
	//
	//		Account account =
	//				given()
	//				.when().get("/accounts/{accountNumber}", 5465)
	//				.then()
	//				.statusCode(200)
	//				.extract()
	//				.as(Account.class);
	//
	//		assertThat(account.getAccountNumber(), equalTo(5465L));
	//		assertThat(account.getCustomerName(), equalTo("Alex Trebek"));
	//		assertThat(account.getCustomerNumber(), equalTo(776868L));
	//		assertThat(account.getBalance(), equalTo(new BigDecimal("0.00")));
	//		assertThat(account.getAccountStatus(), equalTo(AccountStatus.CLOSED));
	//	}
	//
	//	@Test
	//	@Order(5)
	//	void testDeposit() {
	//		Account account =
	//				given()
	//				.when().get("/accounts/{accountNumber}", 123456789)
	//				.then()
	//				.statusCode(200)
	//				.extract()
	//				.as(Account.class);
	//
	//		BigDecimal deposit = new BigDecimal("154.98");
	//		BigDecimal balance = account.getBalance().add(deposit);
	//
	//		account =
	//				given()
	//				.contentType(ContentType.JSON)
	//				.body(deposit.toString())
	//				.when().put("/accounts/{accountNumber}/deposit", 123456789)
	//				.then()
	//				.statusCode(200)
	//				.extract()
	//				.as(Account.class);
	//
	//		assertThat(account.getAccountNumber(), equalTo(123456789L));
	//		assertThat(account.getCustomerName(), equalTo("Debbie Hall"));
	//		assertThat(account.getCustomerNumber(), equalTo(12345L));
	//		assertThat(account.getAccountStatus(), equalTo(AccountStatus.OPEN));
	//		assertThat(account.getBalance(), equalTo(balance));
	//
	//		account =
	//				given()
	//				.when().get("/accounts/{accountNumber}", 123456789)
	//				.then()
	//				.statusCode(200)
	//				.extract()
	//				.as(Account.class);
	//
	//		assertThat(account.getAccountNumber(), equalTo(123456789L));
	//		assertThat(account.getCustomerName(), equalTo("Debbie Hall"));
	//		assertThat(account.getCustomerNumber(), equalTo(12345L));
	//		assertThat(account.getAccountStatus(), equalTo(AccountStatus.OPEN));
	//		assertThat(account.getBalance(), equalTo(balance));
	//	}
	//
	//	@Test
	//	@Order(6)
	//	void testWithdrawal() {
	//		Account account =
	//				given()
	//				.when().get("/accounts/{accountNumber}", 78790)
	//				.then()
	//				.statusCode(200)
	//				.extract()
	//				.as(Account.class);
	//
	//		BigDecimal withdrawal = new BigDecimal("23.82");
	//		BigDecimal balance = account.getBalance().subtract(withdrawal);
	//
	//		account =
	//				given()
	//				.contentType(ContentType.JSON)
	//				.body(withdrawal.toString())
	//				.when().put("/accounts/{accountNumber}/withdrawal", 78790)
	//				.then()
	//				.statusCode(200)
	//				.extract()
	//				.as(Account.class);
	//
	//		assertThat(account.getAccountNumber(), equalTo(78790L));
	//		assertThat(account.getCustomerName(), equalTo("Vanna White"));
	//		assertThat(account.getCustomerNumber(), equalTo(444222L));
	//		assertThat(account.getAccountStatus(), equalTo(AccountStatus.OPEN));
	//		assertThat(account.getBalance(), equalTo(balance));
	//
	//		account =
	//				given()
	//				.when().get("/accounts/{accountNumber}", 78790)
	//				.then()
	//				.statusCode(200)
	//				.extract()
	//				.as(Account.class);
	//
	//		assertThat(account.getAccountNumber(), equalTo(78790L));
	//		assertThat(account.getCustomerName(), equalTo("Vanna White"));
	//		assertThat(account.getCustomerNumber(), equalTo(444222L));
	//		assertThat(account.getAccountStatus(), equalTo(AccountStatus.OPEN));
	//		assertThat(account.getBalance(), equalTo(balance));
	//	}
	//
	//	@Test
	//	void testGetAccountFailure() {
	//		given()
	//		.when().get("/accounts/{accountNumber}", 11)
	//		.then()
	//		.statusCode(404);
	//	}
	//
	//	@Test
	//	void testCreateAccountFailure() {
	//		Account newAccount = new Account();
	//		newAccount.setId(12L);
	//		newAccount.setAccountNumber(90909L);
	//		newAccount.setCustomerNumber(888898L);
	//		newAccount.setCustomerName("Barry Mines");
	//		newAccount.setBalance(new BigDecimal("878.32"));
	//
	//		given()
	//		.contentType(ContentType.JSON)
	//		.body(newAccount)
	//		.when().post("/accounts")
	//		.then()
	//		.statusCode(400);
	//	}
}
