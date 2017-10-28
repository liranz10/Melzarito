package Logic;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import static android.R.attr.category;


public class RestaurantManager {
     FirebaseDatabase database ;
     DatabaseReference db ;
     Menu menu;
     int orderIdCounter=0;
     int orderItemIdCounter=0;
     ArrayList<Table> tables;
     ArrayList<Order> orders = new ArrayList<>();
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

//             GenericTypeIndicator<ArrayList<OrderItem>> order_item_type = new GenericTypeIndicator<ArrayList<OrderItem>>() {};
//             orders =dataSnapshot.child("Orders").getValue(order_item_type);
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
        // add the notes to DB

    }

    public void CreateNewOrderAndWriteToDB(int tableNumber) {
        orderIdCounter++;
        db.child("counterOrderID").setValue(orderIdCounter);
        Order new_order = new Order(orderIdCounter,tableNumber,1,1,new Date(),1,true);
        orders.add(new_order);
        db.child("Orders").child(new_order.getId()+"").child("Table number").setValue(new_order.getTableNumber());
        db.child("Orders").child(new_order.getId()+"").child("lastModifiedTime").setValue(new_order.getLastModifiedTime());
        db.child("Orders").child(new_order.getId()+"").child("isOpen").setValue(new_order.isOpen());
        db.child("Orders").child(new_order.getId()+"").child("Order items").setValue(new_order.getOrderItems());


    }
}
