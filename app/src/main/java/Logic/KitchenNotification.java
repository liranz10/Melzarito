package Logic;


public class KitchenNotification {
    private int orderId;
    private int tableNumber;
    private boolean invoked = false;

    public KitchenNotification() {
    }

    public KitchenNotification(int orderId, int tableNumber, boolean wasInvoked) {
        this.orderId = orderId;
        this.tableNumber = tableNumber;
        this.invoked = wasInvoked;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public boolean isInvoked() {
        return invoked;
    }
}
