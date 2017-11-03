package Logic;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import sapir_liran.melzarito.UI.LoginActivity;
import sapir_liran.melzarito.UI.OpenOrdersFragment;

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
     private boolean readfromDB = false;
     static HashMap<Integer,Order> orders = new LinkedHashMap<>();

    private boolean hasDataChanged;
    //HashMap<String, Order> ordersHashMapStringKey = new HashMap<>();
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

             //--sapir: need to check why we cannot load this 2 integers in the first time from DB
             if(dataSnapshot.child("counterOrderID").getValue() == null)
                 db.child("counterOrderID").setValue(0);
             long longOrderIdCounter = (long) dataSnapshot.child("counterOrderID").getValue();

             if(dataSnapshot.child("counterOrderItemsID").getValue() == null)
                 db.child("counterOrderItemsID").setValue(0);
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
                    for (final Integer order_id : ids) {

                        GenericTypeIndicator<Order> order_type= new GenericTypeIndicator<Order>(){};
                        final Order curr_order =dataSnapshot.child("Orders").child(order_id.toString()).getValue(order_type);
                        final Integer[] orderItemsIds = new Integer[(int)((long)dataSnapshot.child("Orders").child(order_id+"").child("Order items").getChildrenCount())];
                        i=0;
                        //if(orderItemsIds.length >0) {
                            for (DataSnapshot d : dataSnapshot.child("Orders").child(order_id + "").child("Order items").getChildren()) {
                                orderItemsIds[i] = Integer.parseInt(d.getKey());
                                i++;
                            }
                      //  }

                        final Query query = db.child("Orders").child(order_id+"").child("Order items");

                        query.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                if(readfromDB) {
                                    if (dataSnapshot.getValue() != null) {
//
                                        try {
                                            GenericTypeIndicator<Date> date_type = new GenericTypeIndicator<Date>() {
                                            };
                                            Date tempLastModified = dataSnapshot.child("lastModifiedTime").getValue(date_type);
                                            String tempName = (String) dataSnapshot.child("name").getValue();
                                            int tempCategory = (int) ((long) dataSnapshot.child("category").getValue());
                                            int tempMenuId = (int) ((long) dataSnapshot.child("MenuItemId").getValue());
                                            int orderItem_id = Integer.parseInt(dataSnapshot.getKey());
                                            curr_order.addOrderItem(new OrderItem(tempMenuId, tempName, tempCategory, orderItem_id, tempLastModified, new ArrayList<String>()));
                                        }catch (NullPointerException ex){}

//
                                    }
                                }
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        if (curr_order.isOpen()) {
                            orders.put(order_id, curr_order);
                        }

                    }
                }
            }
//            if(OpenOrdersFragment.tablesAdapter!=null)
//             OpenOrdersFragment.update();
         }

         @Override
         public void onCancelled(DatabaseError error) {
         }
     };



    public RestaurantManager(){
        database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(false);
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

        OrderItem new_item = new OrderItem(item.getId(),item.getName(),category,orderItemIdCounter,new Date(),new ArrayList<String>());

        readfromDB=false;

        db.child("Orders").child(orderIdCounter+"").child("Order items").child(orderItemIdCounter+"").child("category").setValue(new_item.getCategory());
        db.child("Orders").child(orderIdCounter+"").child("Order items").child(orderItemIdCounter+"").child("MenuItemId").setValue(new_item.getId());
        db.child("Orders").child(orderIdCounter+"").child("Order items").child(orderItemIdCounter+"").child("lastModifiedTime").setValue(new_item.getLastModifiedTime());
        db.child("Orders").child(orderIdCounter+"").child("Order items").child(orderItemIdCounter+"").child("name").setValue(new_item.getName());

        orderItemIdCounter++;

        db.child("counterOrderItemsID").setValue(orderItemIdCounter);
        // add the notes to DB
        readfromDB=true;

    }

      public void CreateNewOrderAndWriteToDB(int tableNumber) {
          orderIdCounter++;
          readfromDB=false;
          db.child("counterOrderID").setValue(orderIdCounter);
          Order new_order = new Order(orderIdCounter,tableNumber, LoginActivity.loggedInUserName,new Date(),1,true);
          db.child("Orders").child(new_order.getId()+"").setValue(new_order);
          readfromDB=true;


    }
    @NonNull
    public static Collection<Order> getOpenOrders(){
        return orders.values();
    }


}
