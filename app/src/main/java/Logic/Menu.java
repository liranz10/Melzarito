package Logic;


import java.util.ArrayList;
import java.util.Date;

public class Menu {

    private int id;
    private ArrayList<MenuItem> items;

    public Menu() {
    }

    public Menu(int id, ArrayList<MenuItem> items) {
        this.id = id;
        this.items = new ArrayList<>();
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



}
