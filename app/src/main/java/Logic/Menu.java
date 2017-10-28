package Logic;


import java.util.ArrayList;
import java.util.Date;

public class Menu {

    private int id; //--sapir: need this?
    private ArrayList<MenuItem> items;

    public Menu() {
    }

    public Menu(int id, ArrayList<MenuItem> items) {
        this.id = id;
        this.items =items;
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

    public int getItemsCountOnCategory(int category){
        int counter=0;
        for(MenuItem item : items) {
            if(item.getCategory()==category)
                counter++;

        }
        return counter;
    }

    public ArrayList<MenuItem> getItemsOnCategory(int category){

        ArrayList<MenuItem> itemsOnCategory = new ArrayList<>();
        for(MenuItem item : items) {
            if(item.getCategory()==category)
                itemsOnCategory.add(item);

        }
        return itemsOnCategory;
    }

    public String getItemNameByID(int id){
        for(MenuItem item : items) {
            if(item.getId()==id)
                return item.getName();
        }
        return "";
    }


}
