package Logic;


import java.util.ArrayList;

public class Menu {

    private int id;
    private ArrayList<MenuItem> items;

    public Menu() {
    }

    public Menu(int id, ArrayList<MenuItem> items) {
        this.id = id;
        this.items = items;
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


    public ArrayList<MenuItem> getItemsOnCategory(int category) {

        ArrayList<MenuItem> itemsOnCategory = new ArrayList<>();
        for (MenuItem item : items) {
            if (item != null) {
                if (item.getCategory() == category)
                    itemsOnCategory.add(item);
            }

        }
        return itemsOnCategory;
    }

}
