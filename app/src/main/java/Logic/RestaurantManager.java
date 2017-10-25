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
        ArrayList<ArrayList<MenuItem>> items= new ArrayList<>();
        ArrayList<MenuItem> firstDish = new ArrayList<>();
        firstDish.add(new MenuItem(1, "חציל בטחינה", 1));

        ArrayList<MenuItem> mainDish = new ArrayList<>();
        mainDish.add(new MenuItem(1, "שניצל", 2));

        ArrayList<MenuItem> desert = new ArrayList<>();
        desert.add(new MenuItem(1, "עוגה", 3));

        items.add(firstDish);
        menu = new Menu(1,items);
        db.child("Menu").child("קינוחים").setValue(desert);
        db.child("Menu").child("מנות עקריות").setValue(mainDish);
        db.child("Menu").child("מנות ראשונות").setValue(firstDish);


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

    public Menu getMenu()
    {
        return menu;
    }


}
