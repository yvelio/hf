package com.yvelio.hands.repository.tests;

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

import com.yvelio.hands.repository.Hand;
import com.yvelio.hands.repository.HandSite;

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
						containsString("Chimaera V"),
						containsString("Chimaera V"),
						containsString("Chimaera V"),
						containsString("Clematis"),
						containsString("Clematis"),
						containsString("Clematis")
						)
				.extract()
				.response();

		List<Hand> accounts = result.jsonPath().getList("$");
		assertThat(accounts, not(empty()));
		assertThat(accounts, hasSize(8));
	}

	@Test
	@Order(2)
	void testGetAccount() {
		Hand account =
				given()
				.when().get("/hands/{handNumber}", 223914288853L)
				.then()
				.statusCode(200)
				.extract()
				.as(Hand.class);

		assertThat(account.getHandNumber(), equalTo(223914288853L));
		assertThat(account.getTableName(), equalTo("Chimaera V"));
		assertThat(account.getHandSite(), equalTo(HandSite.POKERSTARS));
	}

	@Test
	@Order(3)
	void testCreateAccount() throws Exception {
		Hand newAccount = new Hand();
		newAccount.setHandNumber(324324L);
		newAccount.setTableName("Test table");
		newAccount.setHandSite(HandSite.UNKNOWN);

		Hand returnedAccount =
				given()
				.contentType(ContentType.JSON)
				.body(newAccount)
				.when().post("/hands")
				.then()
				.statusCode(201)
				.extract()
				.as(Hand.class);

		assertThat(returnedAccount, notNullValue());
		newAccount.setId(returnedAccount.getId());
		assertThat(returnedAccount, equalTo(newAccount));

		Response result =
				given()
				.when().get("/hands")
				.then()
				.statusCode(200)
				.body(
						containsString("Chimaera V"),
						containsString("Chimaera V"),
						containsString("Chimaera V"),
						containsString("Chimaera V"),
						containsString("Chimaera V"),
						containsString("Clematis"),
						containsString("Clematis"),
						containsString("Clematis"),
						containsString("Test table")
						)
				.extract()
				.response();

		List<Hand> accounts = result.jsonPath().getList("$");
		assertThat(accounts, not(empty()));
		assertThat(accounts, hasSize(9));
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
