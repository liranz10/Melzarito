package Logic;


import java.util.ArrayList;
import java.util.Date;

public class OrderItem { //--sapir: maybe should extends from MenuItem

    private int orderID;
    private int menuItemID;
    private Date lastModifiedTime;
    private int lastModifiedBy; //--sapir: why do we need this?
    private ArrayList<String> notes;

    public OrderItem() {
    }

    public OrderItem(int orderID, int menuItemID, Date lastModifiedTime, int lastModifiedBy, ArrayList<String> notes) {
        this.orderID = orderID;
        this.menuItemID = menuItemID;
        this.lastModifiedTime = lastModifiedTime;
        this.lastModifiedBy = lastModifiedBy;
        this.notes = notes;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getMenuItemID() {
        return menuItemID;
    }

    public void setMenuItemID(int menuItemID) {
        this.menuItemID = menuItemID;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public int getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(int lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public ArrayList<String> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<String> notes) {
        this.notes = notes;
    }
}
