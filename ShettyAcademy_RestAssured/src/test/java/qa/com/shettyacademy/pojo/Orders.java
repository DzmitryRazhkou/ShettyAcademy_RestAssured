package qa.com.shettyacademy.pojo;

import java.util.ArrayList;

public class Orders {

    public ArrayList<Order> orders;

    public Orders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
}
