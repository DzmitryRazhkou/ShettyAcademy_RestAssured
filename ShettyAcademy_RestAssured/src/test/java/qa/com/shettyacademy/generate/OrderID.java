package qa.com.shettyacademy.generate;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import qa.com.shettyacademy.token.Token;

import java.io.File;

import static io.restassured.RestAssured.given;

public class OrderID {

    public static String generate() {
        String token = Token.generate("dimagadjilla@gmail.com", "3036057Dr");
        Response response =
                given().log().all()
                        .baseUri("https://rahulshettyacademy.com/")
                        .contentType(ContentType.JSON)
                        .header("Authorization", token)
                        .body(new File("src/test/java/qa/com/shettyacademy/jsonschema_contract/createBody.json"))
                        .when().log().all()
                        .post("api/ecom/order/create-order")
                        .then().log().all()
                        .assertThat()
                        .statusCode(201)
                        .and()
                        .extract()
                        .response();

        JsonPath json = response.jsonPath();
        String orderId = json.getString("orders").replaceAll("[\\[\\](){}]", "");
        System.out.println(" =====> " + orderId + " <===== ");
        return orderId;
    }
}
