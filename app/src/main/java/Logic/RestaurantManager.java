package Logic;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class RestaurantManager {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference db = database.getReference();
    private Menu menu;


    public RestaurantManager() {
        ArrayList<MenuItem> items= new ArrayList<>();
        MenuItem item1 = new MenuItem(1,"חציל בטחינה",1);
        items.add(item1);
        menu = new Menu(1,items);
        db.child("Menu").child("1").setValue(menu);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              //loading Menu
//               menu = dataSnapshot.getValue(Menu.class);
//                for(DataSnapshot data : dataSnapshot.getChildren()){
//                    Log.d("onDataChange", data.getValue().toString());
//                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

    }


}
