package qa.com.shettyacademy.token;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import qa.com.shettyacademy.pojo.Product;

import static io.restassured.RestAssured.given;

public class Token {

    public static String generate(String userName, String password) {

        Product.Credentials body = new Product.Credentials(userName, password);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        String userJson = null;
        try {
            userJson = objectMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Response response =
                given()
                        .baseUri("https://rahulshettyacademy.com/")
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .body(userJson)
                        .when()
                        .post("api/ecom/auth/login")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        JsonPath json = response.jsonPath();
        return json.getString("token");

    }
}
