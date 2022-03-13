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

import com.yvelio.enums.PokerSite;
import com.yvelio.hands.repository.Hand;
import com.yvelio.hands.repository.Hero;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
@TestMethodOrder(OrderAnnotation.class)
public class HeroResourceTest {
	@Test
	@Order(1)
	void testRetrieveAll() {
		Response result =
				given()
				.when().get("/heros")
				.then()
				.statusCode(200)
				.body(
						containsString("yvel310"),
						containsString("makkrik")
						)
				.extract()
				.response();

		List<Hand> herros = result.jsonPath().getList("$");
		assertThat(herros, not(empty()));
		assertThat(herros, hasSize(2));
	}

	@Test
	@Order(2)
	void testGetHero() {
		Hero hero =
				given()
				.when().get("/heros/{playerName}", "yvel310")
				.then()
				.statusCode(200)
				.extract()
				.as(Hero.class);

		assertThat(hero.getPlayerName(), equalTo("yvel310"));
		assertThat(hero.getSite(), equalTo(PokerSite.POKERSTARS));
	}

	@Test
	@Order(3)
	void testCreateHero() throws Exception {
		Hero newHero = new Hero();
		newHero.setPlayerName("Cogno");
		newHero.setSite(PokerSite.POKERGENIUS);

		Hand newHand1 = new Hand();
		newHand1.setHandNumber(1L);
		newHand1.setTableName("Test table");
		newHand1.setSite(PokerSite.POKERGENIUS);

		Hand newHand2 = new Hand();
		newHand2.setHandNumber(2L);
		newHand2.setTableName("Test table");
		newHand2.setSite(PokerSite.POKERGENIUS);

		newHero.addToHands(newHand1);
		newHero.addToHands(newHand2);

		Hero returnedHero =
				given()
				.contentType(ContentType.JSON)
				.body(newHero).log().all()
				.when().post("/heros")
				.then()
				.statusCode(201)
				.extract()
				.as(Hero.class);

		
		newHero.setHeroId(returnedHero.getHeroId());

		assertThat(returnedHero, notNullValue());
		assertThat(returnedHero, equalTo(newHero));
		assertThat(returnedHero.getHands(), hasSize(2));

		Response result =
				given()
				.when().get("/heros")
				.then()
				.statusCode(200)
				.body(
						containsString("yvel310"),
						containsString("makkrik"),
						containsString("Cogno")
						)
				.extract()
				.response();

		List<Hero> heros = result.jsonPath().getList("$");
		assertThat(heros, not(empty()));
		assertThat(heros, hasSize(3));
	}
}
