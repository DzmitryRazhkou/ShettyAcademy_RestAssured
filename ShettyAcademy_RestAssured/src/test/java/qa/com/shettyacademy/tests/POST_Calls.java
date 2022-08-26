package qa.com.shettyacademy.tests;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import qa.com.shettyacademy.generate.Password;
import qa.com.shettyacademy.generate.UserID;
import qa.com.shettyacademy.pojo.*;
import qa.com.shettyacademy.token.Token;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class POST_Calls {
    private static String productId;
    private static String token;
    private static String userId;
    private static Faker faker;

    @Test(priority = 5)
    public void registerNewUserTest() {
        faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();
        String role = "customer";
        String phone = faker.numerify("1#########");
        String gender = "Male";
        String occupation = "Engineer";
        boolean required = true;
        String password = Password.generatePassword();

        NewUser newUser = new NewUser(firstName, lastName, email, role, occupation, gender, phone, password, password, required);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        String newUserJson = null;
        try {
            newUserJson = objectMapper.writeValueAsString(newUser);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        String message = "Registered Successfully";
        token = Token.generate("dimagadjilla@gmail.com", "3036057Dr");
        RestAssured.baseURI = "https://rahulshettyacademy.com/";
        given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(newUserJson)
                .when().log().all()
                .post("api/ecom/auth/register")
                .then().log().all()
                .statusCode(200)
                .and()
                .body("message", equalTo(message));
    }

    @Test(priority = 6)
    public void updateUserPasswordTest() {

        String email = "dimagadjilla@gmail.com";
        String password = "3036057Dr";

        NewPassword newPassword = new NewPassword(email, password, password);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        String newPasswordJson = null;
        try {
            newPasswordJson = objectMapper.writeValueAsString(newPassword);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        String message = "Password Changed Successfully";
        token = Token.generate("dimagadjilla@gmail.com", "3036057Dr");
        RestAssured.baseURI = "https://rahulshettyacademy.com/";
        given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(newPasswordJson)
                .when().log().all()
                .post("api/ecom/auth/new-password")
                .then().log().all()
                .statusCode(200)
                .and()
                .body("message", equalTo(message));
    }

    @Test(priority = 1)
    public void loginCallTest() {

        String email = "dimagadjilla@gmail.com";
        String password = "3036057Dr";

        Product.Credentials body = new Product.Credentials(email, password);
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
                        .and()
                        .body("userId", equalTo("62cb9910e26b7e1a10f10d41"))
                        .extract()
                        .response();

        JsonPath json = response.jsonPath();
        userId = json.getString("userId");
        System.out.println(" =====> " + userId + " <===== ");

    }

    @Test(enabled = false)
    public void validateGetAllProductsJSONSchemaTest() {

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

    @Test(priority = 2)
    public void getAllProductsTest() {

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

    @Test(priority = 3)
    public void addProductToCartTest() {

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

    @Test(priority = 4)
    public void createProductOrderTest() {
        token = Token.generate("dimagadjilla@gmail.com", "3036057Dr");
        String country = "Ukraine";
        String successMessage = "Order Placed Successfully";

        Order order = new Order(country, productId);
        List<Order> list = Collections.singletonList(order);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        String ordersJson = null;
        try {
            ordersJson = mapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

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
                        .body("message", equalTo(successMessage))
                        .extract()
                        .response();

        JsonPath json = response.jsonPath();
        String orderId = json.getString("orders").replaceAll("[\\[\\](){}]", "");
        System.out.println(" =====> " + orderId + " <===== ");

    }
}
