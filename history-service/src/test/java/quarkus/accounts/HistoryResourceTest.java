package quarkus.accounts;

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

import com.yvelio.histories.Hand;
import com.yvelio.histories.HandSite;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@QuarkusTest
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
						containsString("Clematis"),
						containsString("Clematis"),
						containsString("Chimaera V"),
						containsString("Chimaera V"),
						containsString("Chimaera V")
						)
				.extract()
				.response();

		List<Hand> histories = result.jsonPath().getList("$");
		assertThat(histories, not(empty()));
		assertThat(histories, hasSize(5));
	}

	@Test
	@Order(2)
	void testGetHistory() {
		Hand account =
				given()
				.when().get("/histories/{handNumber}", 223914243616L)
				.then()
				.statusCode(200)
				.extract()
				.as(Hand.class);

		assertThat(account.getHandNumber(), equalTo(223914243616L));
		assertThat(account.getTableName(), equalTo("Chimaera V"));
		assertThat(account.getHandSite(), equalTo(HandSite.POKERSTARS));
	}

	@Test
	@Order(3)
	void testCreateHistory() {
		Hand newHand = new Hand(324324L, "Test table", HandSite.UNKNOWN);

		Hand returnedAccount =
				given()
				.contentType(ContentType.JSON)
				.body(newHand)
				.when().post("/histories")
				.then()
				.statusCode(201)
				.extract()
				.as(Hand.class);

		assertThat(returnedAccount, notNullValue());
		assertThat(returnedAccount, equalTo(newHand));

		Response result =
				given()
				.when().get("/histories")
				.then()
				.statusCode(200)
				.body(
						containsString("Clematis"),
						containsString("Clematis"),
						containsString("Chimaera V"),
						containsString("Chimaera V"),
						containsString("Chimaera V"),
						containsString("Test table")
						)
				.extract()
				.response();

		List<Hand> histories = result.jsonPath().getList("$");
		assertThat(histories, not(empty()));
		assertThat(histories, hasSize(6));
	}

//	@Test
//	@Order(4)
//	void testAccountWithdraw() {
//		Account account =
//				given()
//				.when().get("/accounts/{accountNumber}", 545454545)
//				.then()
//				.statusCode(200)
//				.extract()
//				.as(Account.class);
//
//		assertThat(account.getAccountNumber(), equalTo(545454545L));
//		assertThat(account.getCustomerName(), equalTo("Diana Rigg"));
//		assertThat(account.getBalance(), equalTo(new BigDecimal("422.00")));
//		assertThat(account.getAccountStatus(), equalTo(AccountStatus.OPEN));
//
//		Account result =
//				given()
//				.body("56.21")
//				.when().put("/accounts/{accountNumber}/withdrawal", 545454545)
//				.then()
//				.statusCode(200)
//				.extract()
//				.as(Account.class);
//
//		assertThat(result.getAccountNumber(), equalTo(545454545L));
//		assertThat(result.getCustomerName(), equalTo("Diana Rigg"));
//		assertThat(result.getBalance(), equalTo(account.getBalance().subtract(new BigDecimal("56.21"))));
//		assertThat(result.getAccountStatus(), equalTo(AccountStatus.OPEN));
//	}
//
//	@Test
//	@Order(4)
//	void testAccountDeposit() {
//		Account account =
//				given()
//				.when().get("/accounts/{accountNumber}", 123456789)
//				.then()
//				.statusCode(200)
//				.extract()
//				.as(Account.class);
//
//		assertThat(account.getAccountNumber(), equalTo(123456789L));
//		assertThat(account.getCustomerName(), equalTo("George Baird"));
//		assertThat(account.getBalance(), equalTo(new BigDecimal("354.23")));
//		assertThat(account.getAccountStatus(), equalTo(AccountStatus.OPEN));
//
//		Account result =
//				given()
//				.body("28.42")
//				.when().put("/accounts/{accountNumber}/deposit", 123456789)
//				.then()
//				.statusCode(200)
//				.extract()
//				.as(Account.class);
//
//		assertThat(result.getAccountNumber(), equalTo(123456789L));
//		assertThat(result.getCustomerName(), equalTo("George Baird"));
//		assertThat(result.getBalance(), equalTo(account.getBalance().add(new BigDecimal("28.42"))));
//		assertThat(result.getAccountStatus(), equalTo(AccountStatus.OPEN));
//	}
}
