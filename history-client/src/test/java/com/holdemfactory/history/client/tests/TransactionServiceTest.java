package com.holdemfactory.history.client.tests;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@QuarkusTestResource(WiremockHistoryClientService.class)
public class TransactionServiceTest {
  @Test
  void testTransaction() {
        given()
            .body("142.12")
            .contentType(ContentType.JSON)
            .when().post("/transactions/{accountNumber}", 121212)
            .then()
            .statusCode(200);
  }
}
