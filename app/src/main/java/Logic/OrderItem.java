package Logic;


import java.util.ArrayList;
import java.util.Date;

public class OrderItem extends MenuItem {

    private int orderItemID;
    private Date lastModifiedTime;

    public OrderItem() {
    }

    public OrderItem(int id, String name, int category, int orderItemID, Date lastModifiedTime, ArrayList<String> notes) {
        super(id, name, category);
        this.orderItemID = orderItemID;
        this.lastModifiedTime = lastModifiedTime;
    }

    public int getOrderItemID() {
        return orderItemID;
    }

    public void setOrderItemID(int orderItemID) {
        this.orderItemID = orderItemID;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }
}
