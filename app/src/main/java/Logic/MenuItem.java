package Logic;

import java.util.Date;


public class MenuItem {
    private int id; //--sapir: what is the different between this and menuID ?
    private String name;
    private int category;


    public MenuItem() {
    }

    public MenuItem(int id, String name, int category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }




}
