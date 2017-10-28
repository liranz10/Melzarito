package Logic;


import java.util.ArrayList;
import java.util.Date;

public class OrderItem extends MenuItem {

    private int orderItemID;
    private Date lastModifiedTime;
    private ArrayList<String> notes;

    public OrderItem() {
    }

    public OrderItem(int id, String name, int category, int orderItemID, Date lastModifiedTime, ArrayList<String> notes) {
        super(id, name, category);
        this.orderItemID = orderItemID;
        this.lastModifiedTime = lastModifiedTime;
        this.notes = notes;
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


    public ArrayList<String> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<String> notes) {
        this.notes = notes;
    }
}
