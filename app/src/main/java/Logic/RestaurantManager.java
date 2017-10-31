package Logic;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import sapir_liran.melzarito.UI.LoginActivity;

import static android.R.attr.category;
import static android.R.attr.name;
import static android.R.attr.order;
import static sapir_liran.melzarito.R.id.new_order;


public class RestaurantManager {
     FirebaseDatabase database ;
     DatabaseReference db ;
     Menu menu;
     int orderIdCounter=0;
     int orderItemIdCounter=0;
     ArrayList<Table> tables;
    private boolean readfromDB = true;
     static HashMap<Integer,Order> orders = new LinkedHashMap<>();
     ValueEventListener listener = new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
             //loading Menu
             long idl =(long) dataSnapshot.child("Menu").child("id").getValue();
             int id = (int)idl;
             GenericTypeIndicator<ArrayList<MenuItem>> items_type = new GenericTypeIndicator<ArrayList<MenuItem>>() {};
             ArrayList<MenuItem> items = dataSnapshot.child("Menu").child("Items").getValue(items_type);
             menu = new Menu(id,items);
             //loading Tables
             GenericTypeIndicator<ArrayList<Table>> table_type = new GenericTypeIndicator<ArrayList<Table>>() {};
             tables =dataSnapshot.child("Tables").getValue(table_type);
             long longOrderIdCounter = (long) dataSnapshot.child("counterOrderID").getValue();
             long longOrderItemIdCounter = (long) dataSnapshot.child("counterOrderItemsID").getValue();
             orderIdCounter = (int) longOrderIdCounter;
             orderItemIdCounter = (int) longOrderItemIdCounter;

            if(readfromDB) {
                if (dataSnapshot.child("Orders").getValue() != null) {
                    Integer[] ids = new Integer[(int) ((long) dataSnapshot.child("Orders").getChildrenCount())];
                    int i = 0;
                    for (DataSnapshot d : dataSnapshot.child("Orders").getChildren()) {
                        ids[i] = Integer.parseInt(d.getKey());
                        i++;
                    }
                    for (Integer order_id : ids) {

                        GenericTypeIndicator<Order> order_type= new GenericTypeIndicator<Order>(){};
                        Order curr_order =(Order) dataSnapshot.child("Orders").child(order_id.toString()).getValue(order_type);


                        if (curr_order.isOpen()) {
                          orders.put(order_id, curr_order);
                        }

                    }
                }
            }
         }

         @Override
         public void onCancelled(DatabaseError error) {
         }
     };
    public RestaurantManager(){
        database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        db = database.getReference();
        db.keepSynced(true);
        db.addValueEventListener(listener);

    }

    public Menu getMenu() {
        return menu;
    }

    public ArrayList<Table> getTables() {
        return tables;
    }

    public void createOrderItemAndWriteToDB(MenuItem item,int category) {
        orderItemIdCounter++;
        OrderItem new_item = new OrderItem(item.getId(),item.getName(),category,orderItemIdCounter,new Date(),new ArrayList<String>());

        db.child("counterOrderItemsID").setValue(orderItemIdCounter);

        db.child("Orders").child(orderIdCounter+"").child("Order items").child(orderItemIdCounter+"").child("lastModifiedTime").setValue(new_item.getLastModifiedTime());
        db.child("Orders").child(orderIdCounter+"").child("Order items").child(orderItemIdCounter+"").child("name").setValue(new_item.getName());
        db.child("Orders").child(orderIdCounter+"").child("Order items").child(orderItemIdCounter+"").child("category").setValue(new_item.getCategory());

        orders.get(orderIdCounter).getOrderItems().add(new_item);
        // add the notes to DB

    }

      public void CreateNewOrderAndWriteToDB(int tableNumber) {
        orderIdCounter++;
        readfromDB=false;
        db.child("counterOrderID").setValue(orderIdCounter);
        Order new_order = new Order(orderIdCounter,tableNumber, LoginActivity.loggedInUserName,new Date(),1,true);
        HashMap<String, Order> ttt = new HashMap<>();
          ttt.put(new_order.getId()+"", new_order);
          db.child("Orders").setValue(ttt);
//        db.child("Orders").child(new_order.getId()+"").child("Table number").setValue(new_order.getTableNumber());
//        db.child("Orders").child(new_order.getId()+"").child("lastModifiedTime").setValue(new_order.getLastModifiedTime());
//        db.child("Orders").child(new_order.getId()+"").child("isOpen").setValue(new_order.isOpen());
//        db.child("Orders").child(new_order.getId()+"").child("waiterName").setValue(LoginActivity.loggedInUserName);
//        db.child("Orders").child(new_order.getId()+"").child("Order items").setValue(new_order.getOrderItems());
            readfromDB=true;

    }
    public static Collection<Order> getOpenOrders(){
        return orders.values();
    }


}
