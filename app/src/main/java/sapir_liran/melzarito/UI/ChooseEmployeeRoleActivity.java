package sapir_liran.melzarito.UI;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import sapir_liran.melzarito.R;

public class ChooseEmployeeRoleActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private Button btnLogout,chefbtn,waiterbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_employee);

        auth = FirebaseAuth.getInstance();
        btnLogout = (Button) findViewById(R.id.logout);
        chefbtn = (Button)findViewById(R.id.chefsButton);
        waiterbtn = (Button)findViewById(R.id.waitersButton);
        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/SecularOne-Regular.ttf");

        waiterbtn.setTypeface(tf,Typeface.BOLD);
        chefbtn.setTypeface(tf,Typeface.BOLD);
        btnLogout.setTypeface(tf,Typeface.BOLD);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(ChooseEmployeeRoleActivity.this, LoginActivity.class));
                finish();
            }
        });

        waiterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseEmployeeRoleActivity.this, WaitersMainActivity.class));
                finish();
            }
        });
    }
}
