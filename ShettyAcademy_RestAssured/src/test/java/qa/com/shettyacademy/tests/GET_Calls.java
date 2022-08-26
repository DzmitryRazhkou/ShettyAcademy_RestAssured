package qa.com.shettyacademy.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import qa.com.shettyacademy.generate.OrderID;
import qa.com.shettyacademy.generate.ProductID;
import qa.com.shettyacademy.token.Token;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

public class GET_Calls {
    private static String orderId;
    private static String productId;
    private static String token;

    @Test(priority = 1)
    public void getProductDetailsTest() {

        token = Token.generate("dimagadjilla@gmail.com", "3036057Dr");
        productId = ProductID.GenerateProductID();
        String successMessage = "Product Details fetched Successfully";

        RestAssured.baseURI = "https://rahulshettyacademy.com/";
        given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .when().log().all()
                .get("api/ecom/product/get-product-detail/" + productId)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .body("data._id", equalTo(productId))
                .body("data.productName", equalTo("iphone 13 pro"))
                .body("message", equalTo(successMessage));
    }

    @Test(priority = 2)
    public static void getAllCustomerOrders() {

        token = Token.generate("dimagadjilla@gmail.com", "3036057Dr");
        String unauthorizedMessage = "You are not authorize to view these orders";
        orderId = OrderID.generate();

        RestAssured.baseURI = "https://rahulshettyacademy.com/";
        given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .when().log().all()
                .get("api/ecom/order/get-orders-for-customer/" + orderId)
                .then().log().all()
                .assertThat()
                .statusCode(403)
                .and()
                .contentType(ContentType.JSON)
                .body("message", equalTo(unauthorizedMessage));
    }
}
