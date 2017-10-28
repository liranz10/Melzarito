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


public class RestaurantManager {
     FirebaseDatabase database ;
     DatabaseReference db ;
     Menu menu;
     ArrayList<Table> tables;
    ArrayList<OrderItem> orders;
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

    public void writeOrderData(int orderId, int menuItemId, Date lastModifiedTime) {
        db.child("Orders").child(orderId+"").child("menuItemId").setValue(menuItemId);
        db.child("Orders").child(orderId+"").child("lastModifiedTime").setValue(lastModifiedTime);

    }
}
