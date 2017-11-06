package sapir_liran.melzarito.UI;

/**
 * Created by Liran on 04/11/2017.
 */

public class SpecialNotification {
    private int id;
    private String name;
    private boolean invoked=false;

    public SpecialNotification() {
    }

    public SpecialNotification(int id, String name, boolean invoked) {
        this.id = id;
        this.name = name;
        this.invoked = invoked;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isInvoked() {
        return invoked;
    }
}
