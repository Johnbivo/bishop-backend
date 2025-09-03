import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UserAddressTests {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://localhost:4001";
    }


}
