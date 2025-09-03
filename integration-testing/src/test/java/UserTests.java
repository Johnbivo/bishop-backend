import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;



import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class UserTests {


    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://localhost:4001/";
        RestAssured.defaultParser = io.restassured.parsing.Parser.JSON;
    }


    @Test
    public void createUserAndReturnHttpCreated201() {

        String createUserPayload = """
                {
                  "firstName": "Yanis",
                  "lastName": "Bivolaris",
                  "email": "yanis@example.com",
                  "phone": "+30 694 123 4567",
                  "gender": "MALE",
                  "dateOfBirth": "2002-05-15",
                  "marketingEmails": true,
                  "orderNotifications": false
                }
                """;

        Response response = given()
                .contentType(ContentType.JSON)
                .body(createUserPayload)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .extract()
                .response();

    }


    @Test
    public void shouldReturnAllUsersAndHttpOk200() {

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .response();

        System.out.println("All users: " + response.getBody().asString());


        List<?> users = response.jsonPath().getList("$");
        assertFalse(users.isEmpty(), "Expected at least one user in the list");

    }

    @Test
    public void shouldCreateUpdateAndGetUser() {
        // Step 1: Create a user
        String createUserPayload = """
    {
      "firstName": "Yanis",
      "lastName": "Bivolaris",
      "email": "yanis.update@example.com",
      "phone": "+30 694 111 1111",
      "gender": "MALE",
      "dateOfBirth": "2002-05-15",
      "marketingEmails": true,
      "orderNotifications": false
    }
    """;

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(createUserPayload)
                .when()
                .post("/users")
                .then()
                .statusCode(201);

        // Step 2: Get all users and find the ID
        List<?> users = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("$");

        // Find the user ID by email
        String userId = users.stream()
                .map(user -> ((Map<String, ?>) user).get("id").toString())
                .filter(id -> {
                    Map<String, ?> u = (Map<String, ?>) users.stream()
                            .filter(user -> ((Map<String, ?>) user).get("id").toString().equals(id))
                            .findFirst()
                            .orElse(null);
                    return u != null && u.get("email").equals("yanis.update@example.com");
                })
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Created user not found"));

        // Step 3: Update the user
        String updateUserPayload = """
    {
      "firstName": "John",
      "lastName": "Doe",
      "phone": "+30 694 222 2222"
    }
    """;

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(updateUserPayload)
                .when()
                .put("/users/" + userId)
                .then()
                .statusCode(200);
        // Step 4: Get all users again
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/users")
                .then()
                .statusCode(200);
    }



}
