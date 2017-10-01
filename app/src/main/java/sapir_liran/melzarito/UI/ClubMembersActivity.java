package sapir_liran.melzarito.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import sapir_liran.melzarito.R;

public class ClubMembersActivity extends TablesActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_club_members,(LinearLayout)findViewById(R.id.club_layout));    }
}
