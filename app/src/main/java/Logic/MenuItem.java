package Logic;

import java.util.Date;


public class MenuItem {
    private int id; //--sapir: what is the different between this and menuID ?
    private int menuID;
    private int category;
    private Date lastModifiedTime; //--sapir: why do we need this?
    private int lastModifiedBy; //--sapir: why do we need this?

    public MenuItem(int id, int menuID, int category, Date lastModifiedTime, int lastModifiedBy) {
        this.id = id;
        this.menuID = menuID;
        this.category = category;
        this.lastModifiedTime = lastModifiedTime;
        this.lastModifiedBy = lastModifiedBy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMenuID() {
        return menuID;
    }

    public void setMenuID(int menuID) {
        this.menuID = menuID;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
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
