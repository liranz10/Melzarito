package Logic;


import java.util.ArrayList;
import java.util.Date;

public class Menu {

    private int id; //--sapir: need this?
    private ArrayList<ArrayList<MenuItem>> items;

    public Menu() {
    }

    public Menu(int id, ArrayList<ArrayList<MenuItem>> items) {
        this.id = id;
        this.items = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<ArrayList<MenuItem>> getItems() {
        return items;
    }

    public void setItems(ArrayList<ArrayList<MenuItem>> items) {
        this.items = items;
    }



}
