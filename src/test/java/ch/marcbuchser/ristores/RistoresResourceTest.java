package ch.marcbuchser.ristores;



import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;


@QuarkusTest
public class RistoresResourceTest {


    @Test
    public void testRistores() throws Exception {
        // Full text search
        RestAssured.when().get("/ristores/menu/search?pattern=Escalope").then()
                .statusCode(200)
                .body("menuTitle", contains("Menu 1"),
                        "menuContent", contains("Escalope de porc panée (CH), Frites, Pois et carottes"));

        RestAssured.when().get("/ristores/menu/search?pattern=Tortelloni").then()
                .statusCode(200)
                .body("menuTitle", contains("Menu 2"),
                        "menuContent", contains("Tortelloni aux tomates et à la mozzarella, sauce tomate épicée"));


        // Add a Menu
        RestAssured.given()
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .formParam("menuTitle", "Spaghetti Carbonara")
                .formParam("menuContent", "Carbonara")
                .put("/ristores/menu/")
                .then()
                .statusCode(204);

        Integer carbonaraId = RestAssured.when().get("/ristores/menu/search?pattern=spaghetti").then()
                .statusCode(200)
                .body("menuTitle", contains("Spaghetti Carbonara"),
                        "menuContent", contains("Carbonara"))
                .extract().path("[0].id");

                System.out.println("hallo test: " + carbonaraId);
        // Update a Menu
        RestAssured.given()
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .formParam("menuTitle", "Spaghetti Carbonara 2")
                .formParam("menuContent", "Carbonara 2")
                .post("/ristores/author/" + carbonaraId)
                .then()
                .statusCode(404);

        RestAssured.when().get("/ristores/menu/search?pattern=spagh*").then()
                .statusCode(200)
                .body("menuTitle", contains("Spaghetti Carbonara"),
                        "menuContent", contains("Carbonara"));

        // Add a Guest
        RestAssured.given()
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .formParam("guestName", "Wolfgang Amadeus")
                .formParam("menuId", carbonaraId)
                .put("/ristores/guest/")
                .then()
                .statusCode(204);

        RestAssured.when().get("/ristores/guest/search?pattern=wolfgang").then()
                .statusCode(200)
                .body("guestName", contains("Wolfgang Amadeus"));
    }

}