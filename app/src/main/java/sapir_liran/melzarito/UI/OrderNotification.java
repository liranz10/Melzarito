package sapir_liran.melzarito.UI;

/**
 * Created by Liran on 04/11/2017.
 */

public class OrderNotification {
    private int orderId;
    private int tableNumber;
    private boolean invoked=false;

    public OrderNotification() {
    }

    public OrderNotification(int orderId, int tableNumber, boolean wasInvoked) {
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
