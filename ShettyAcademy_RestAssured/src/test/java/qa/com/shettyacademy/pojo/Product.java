package qa.com.shettyacademy.pojo;

public class Product {

    private String _id;
    private String productName;
    private String productCategory;
    private String productSubCategory;
    private int productPrice;
    private String productDescription;
    private String productImage;
    private String productRating;
    private String productTotalOrders;
    private boolean productStatus;
    private String productFor;
    private String productAddedBy;
    private int __v;

    public Product(String _id, String productName, String productCategory, String productSubCategory,
                   int productPrice, String productDescription, String productImage,
                   String productRating, String productTotalOrders, boolean productStatus,
                   String productFor, String productAddedBy, int __v) {
        this._id = _id;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productSubCategory = productSubCategory;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productImage = productImage;
        this.productRating = productRating;
        this.productTotalOrders = productTotalOrders;
        this.productStatus = productStatus;
        this.productFor = productFor;
        this.productAddedBy = productAddedBy;
        this.__v = __v;
    }

    public static class Credentials {

        private String userEmail;
        private String userPassword;

        public Credentials(String userEmail, String userPassword) {
            this.userEmail = userEmail;
            this.userPassword = userPassword;
        }
    }
}
