package qa.com.shettyacademy.tests;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import qa.com.shettyacademy.pojo.Payload;
import qa.com.shettyacademy.pojo.Product;
import qa.com.shettyacademy.token.Credentials;
import qa.com.shettyacademy.token.Token;
import qa.com.shettyacademy.token.UserID;

import java.io.File;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class POST_Calls {

    private static String productId;
    private static String token;
    private static String userId;


    @Test
    public static void loginCall() {

        String email = "dimagadjilla@gmail.com";
        String password = "3036057Dr";

        Credentials body = new Credentials(email, password);
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
                given().log().all()
                        .baseUri("https://rahulshettyacademy.com/")
                        .contentType(ContentType.JSON)
                        .body(userJson)
                        .when().log().all().post("api/ecom/auth/login")
                        .then().log().all()
                        .statusCode(200)
                        .extract()
                        .response();

        JsonPath json = response.jsonPath();
        userId = json.getString("userId");
        System.out.println(" =====> " + userId + " <===== ");

    }

    @Test(enabled = false)
    public static void validateGetAllProductsJSONSchema() {

        token = Token.generate("dimagadjilla@gmail.com", "3036057Dr");
        String path = "/Users/dzmitryrazhkou/Documents/Automation/ShettyAcademy_RestAssured/ShettyAcademy_RestAssured/src/test/java/qa/com/shettyacademy/jsonschema_contract/getAllProductsSchema.json";

        RestAssured.baseURI = "https://rahulshettyacademy.com/";
        given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(new File(path))
                .when().log().all()
                .post("api/ecom/product/get-all-products")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .body(matchesJsonSchemaInClasspath("qa/com/shettyacademy/jsonschema_contract/getAllProductsSchema.json"));
    }

    @Test()
    public static void getAllProductsTest() {
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
        productId = json.getString("data[2]._id");
        System.out.println(" =====> The Product ID: " + productId + " <===== ");
    }

    @Test
    public static void addProductToCartTest() {

        Product product = new Product(productId, "iphone 13 pro", "electronics",
                "shirts", 231500, "iphone 13 pro",
                "https://rahulshettyacademy.com/api/ecom/uploads/productImage_1650649561326.jpg",
                "0", "0", true, "men", "admin@gmail.com", 0);

        userId = UserID.generateUserId("dimagadjilla@gmail.com", "3036057Dr");
        token = Token.generate("dimagadjilla@gmail.com", "3036057Dr");

        Payload payload = new Payload(userId, product);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        String payloadJson = null;
        try {
            payloadJson = mapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        String successMessage = "Product Added To Cart";

        RestAssured.baseURI = "https://rahulshettyacademy.com/";
        given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(payloadJson)
                .when().log().all()
                .post("api/ecom/user/add-to-cart")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and().contentType(ContentType.JSON)
                .body("message", equalTo(successMessage));
    }
}
