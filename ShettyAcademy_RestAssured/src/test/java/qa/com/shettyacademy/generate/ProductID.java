package qa.com.shettyacademy.generate;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import qa.com.shettyacademy.token.Token;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ProductID {

    private static String token;

    public static String GenerateProductID() {
        token = Token.generate("dimagadjilla@gmail.com", "3036057Dr");
        String path = "src/test/java/qa/com/shettyacademy/jsonschema_contract/getAllProductsSchema.json";
        String successMessage = "All Products fetched Successfully";

        Response response =
                given().log().all()
                        .baseUri("https://rahulshettyacademy.com/")
                        .contentType(ContentType.JSON)
                        .header("Authorization", token)
                        .body(new File(path))
                        .when().log().all()
                        .post("api/ecom/product/get-all-products")
                        .then().log().all()
                        .assertThat()
                        .statusCode(200)
                        .and()
                        .contentType(ContentType.JSON)
                        .body("count", equalTo(3))
                        .body("message", equalTo(successMessage))
                        .extract()
                        .response();
        JsonPath json = response.jsonPath();
        String productId = json.getString("data[2]._id");
        System.out.println(" =====> The Product ID: " + productId + " <===== ");
        return productId;
    }
}
