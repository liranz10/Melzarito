package Logic;


import java.util.ArrayList;
import java.util.Date;

public class Order {

    private int id;
    private int tableNumber;
    private int waiterID;
    private int cookerID;
    private Date lastModifiedTime;
    private int lastModifiedBy; //--sapir: why do we need this?
    private boolean open;
    private ArrayList<OrderItem> allOrders; //--sapir: add this

    public Order(int id, int tableNumber, int waiterID, int cookerID, Date lastModifiedTime, int lastModifiedBy, boolean open) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.waiterID = waiterID;
        this.cookerID = cookerID;
        this.lastModifiedTime = lastModifiedTime;
        this.lastModifiedBy = lastModifiedBy;
        this.open = open;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getWaiterID() {
        return waiterID;
    }

    public void setWaiterID(int waiterID) {
        this.waiterID = waiterID;
    }

    public int getCookerID() {
        return cookerID;
    }

    public void setCookerID(int cookerID) {
        this.cookerID = cookerID;
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

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
