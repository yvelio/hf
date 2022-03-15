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
import com.holdemfactory.history.service.repository.History;
import com.holdemfactory.history.service.repository.Player;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
@TestMethodOrder(OrderAnnotation.class)
public class HistoryResourceTest {

	@Test
	@Order(1)
	void testRetrieveAll() {
		Response result =
				given()
				.when().get("/histories")
				.then()
				.statusCode(200)
				.body(
						containsString("HH20210217 Chimaera V - $0.01-$0.02 - USD No Limit Holdem.txt"),
						containsString("HH20201111 Clematis - $0.01-$0.02 - USD No Limit Holdem.txt")
						)
				.extract()
				.response();

		List<History> histories = result.jsonPath().getList("$");
		assertThat(histories, not(empty()));
		assertThat(histories, hasSize(2));
	}

	@Test
	@Order(2)
	void testGetHistory() {
		History history =
				given()
				.when().get("/histories/{historyId}", 1L)
				.then()
				.statusCode(200)
				.extract()
				.as(History.class);

		assertThat(history.getFileName(), equalTo("HH20210217 Chimaera V - $0.01-$0.02 - USD No Limit Holdem.txt"));		
		assertThat(history.getHands(), hasSize(2));
		assertThat(history.getHero(), notNullValue());
	}

	@Test
	@Order(3)
	void testCreateHistory() throws Exception {
		History newHistory = new History();
		newHistory.setFileName("NewHistory");

		Hand newHand = new Hand();
		newHand.setHandNumber(111L);
		newHand.setTableName("Table1");
		newHistory.addToHands(newHand);
		
		Player newPlayer = new Player();
		newPlayer.setPlayerName("yvel310");
		newPlayer.setSite(PokerSite.POKERSTARS);
		newHand.addToPlayers(newPlayer);
		
		Hero hero = new Hero();
		hero.setHistory(newHistory);
		hero.setPlayer(newPlayer);
	
		
		History returnedHistory =
				given()
				.contentType(ContentType.JSON)
				.body(newHistory).log().all()
				.when().post("/histories")
				.then()
				.statusCode(201)
				.extract()
				.as(History.class);
		
		newHistory.setHistoryId(returnedHistory.getHistoryId());
//		newHistory.getHands().iterator().next().setHandId(returnedHistory.getHands().iterator().next().getHandId());
		
		assertThat(returnedHistory, notNullValue());

		assertThat(returnedHistory, equalTo(newHistory));
		assertThat(returnedHistory.getHero(), notNullValue());
		assertThat(returnedHistory.getHands(), hasSize(1));
		
		Response result =
				given()
				.when().get("/histories")
				.then()
				.statusCode(200)
				.body(
						containsString("NewHistory")
						)
				.extract()
				.response();

		List<Hand> histories = result.jsonPath().getList("$");
		assertThat(histories, not(empty()));
		assertThat(histories, hasSize(3));
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
