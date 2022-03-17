package com.holdemfactory.history.service.repository.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

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
						containsString("1"),
						containsString("2")
						)
				.extract()
				.response();

		List<Hand> herros = result.jsonPath().getList("$");
		assertThat(herros, not(empty()));
		assertThat(herros, hasSize(3));
	}

	@Test
	@Order(2)
	void testGetHero() {
		Hero hero =
				given()
				.when().get("/heros/{heroId}", 1)
				.then()
				.statusCode(200)
				.extract()
				.as(Hero.class);

		//BackReference gives no value
		assertThat(hero.getHeroId(), equalTo(1L));
	}

	@Test
	@Order(3)
	void testCreateHero() throws Exception {
		
		Player makkrik = new Player();
		makkrik.setPlayerName("makkrik");

		Hero newHero = new Hero();
		newHero.setPlayer(makkrik);
		
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
		assertThat(returnedHero.getPlayer(), nullValue());

		Response result =
				given()
				.when().get("/heros")
				.then()
				.statusCode(200)
				.body(
						containsString("1"),
						containsString("2"),
						containsString("3")
						)
				.extract()
				.response();

		List<Hero> heros = result.jsonPath().getList("$");
		assertThat(heros, not(empty()));
		assertThat(heros, hasSize(4));
	}
}
