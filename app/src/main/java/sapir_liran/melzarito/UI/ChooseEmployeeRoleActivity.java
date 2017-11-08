package sapir_liran.melzarito.UI;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Logic.RestaurantManager;
import sapir_liran.melzarito.R;

public class ChooseEmployeeRoleActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private Button btnLogout, chefbtn, waiterbtn;
    private RestaurantManager restaurantManager = RestaurantManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_employee);

        auth = FirebaseAuth.getInstance();
        restaurantManager.getLoggedInUserFromDB(auth.getCurrentUser().getUid());


        if (auth.getCurrentUser() != null) {
            btnLogout = (Button) findViewById(R.id.logout);
            chefbtn = (Button) findViewById(R.id.chefsButton);
            waiterbtn = (Button) findViewById(R.id.waitersButton);
            Typeface tf = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.secular_font));
            waiterbtn.setTypeface(tf, Typeface.BOLD);
            chefbtn.setTypeface(tf, Typeface.BOLD);
            btnLogout.setTypeface(tf, Typeface.BOLD);

            //logout
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    auth.signOut();
                    startActivity(new Intent(ChooseEmployeeRoleActivity.this, LoginActivity.class));
                    finish();
                }
            });


            //Go to WaitersMainActivity
            waiterbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ChooseEmployeeRoleActivity.this, WaitersMainActivity.class));
                }
            });

            //Go to KitchenMainActivity
            chefbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ChooseEmployeeRoleActivity.this, KitchenMainActivity.class));
                }
            });
        }
    }


}
