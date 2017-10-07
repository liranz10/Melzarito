package Logic;


import java.util.ArrayList;
import java.util.Date;

public class Menu {

    private int id;
    private ArrayList<MenuItem> items;
    private Date lastModifiedTime; //--sapir: why do we need this?
    private int lastModifiedBy; //--sapir: why do we need this?

    public Menu(int id, ArrayList<MenuItem> items, Date lastModifiedTime, int lastModifiedBy) {
        this.id = id;
        this.items = items;
        this.lastModifiedTime = lastModifiedTime;
        this.lastModifiedBy = lastModifiedBy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<MenuItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<MenuItem> items) {
        this.items = items;
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
}
