package Logic;


public class StockNotification {
    private int itemId;
    private String itemName;
    private boolean invoked = false;

    public StockNotification() {
    }

    public StockNotification(int itemId, String itemName, boolean invoked) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.invoked = invoked;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public boolean isInvoked() {
        return invoked;
    }
}
