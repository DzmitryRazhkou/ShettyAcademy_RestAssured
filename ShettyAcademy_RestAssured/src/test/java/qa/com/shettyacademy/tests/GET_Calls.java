package qa.com.shettyacademy.tests;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

public class GET_Calls {
    private static final String orderId = "63001cc9c4d0c51f4f0f304d";

    private static final String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
            ".eyJfaWQiOiI2MmNiOTkxMGUyNmI3ZTFhMTBmMTBkNDEiLCJ1c2VyRW1haWwiOiJkaW1hZ2FkamlsbGFAZ21haWwuY29tIiwidXNlck1vYmlsZSI6MzIzNDk2MjUxOSwidXNlclJvbGUiOiJjdXN0b21lciIsImlhdCI6MTY2MDg4MDY3MywiZXhwIjoxNjkyNDM4MjczfQ" +
            ".4hrSIb5xsReZ228Ktrugeb8n7IxaHv-AwKpbv7ohXWg";


    @Test
    public void getProductDetailsTest() {

        String productId = "6262e9d9e26b7e1a10e89c04";
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

    @Test
    public static void GetAllCustomerOrders() {

        String unauthorizedMessage = "You are not authorize to view these orders";

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
