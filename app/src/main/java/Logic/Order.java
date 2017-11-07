package Logic;


import java.util.ArrayList;
import java.util.Date;

public class Order {

    private int id;
    private int tableNumber;
    private String waiterName;
    private String cookerName;
    private Date lastModifiedTime;
    private int lastModifiedBy;
    private boolean open;
    private int status;//1 not ready 2 onPrep 3 ready
    private ArrayList<OrderItem> orderItems = new ArrayList<>();

    public Order() {
    }

    public Order(int id, int tableNumber, String waiterName, Date lastModifiedTime, int lastModifiedBy, boolean open, int status) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.waiterName = waiterName;
        this.lastModifiedTime = lastModifiedTime;
        this.lastModifiedBy = lastModifiedBy;
        this.open = open;
        this.status = status;
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

    public String getWaiterName() {
        return waiterName;
    }

    public void setWaiterName(String waiterName) {
        this.waiterName = waiterName;
    }

    public String getCookerName() {
        return cookerName;
    }

    public void setCookerName(String cookerName) {
        this.cookerName = cookerName;
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

    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
