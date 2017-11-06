package sapir_liran.melzarito.UI;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Logic.Customer;
import Logic.Table;
import sapir_liran.melzarito.R;

public class SearchClubMembersFragment extends Fragment {
    View view;
    FirebaseDatabase database;
    DatabaseReference db;
    ArrayList<Customer> members = new ArrayList<>();
    public static int tableNum;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.search_club_members_fragment, container, false);
        getActivity().setTitle(R.string.title_fragment_search_club_member);

        final EditText ed = (EditText) view.findViewById(R.id.club_member_id_text);
        database = FirebaseDatabase.getInstance();
        db = database.getReference();

        ImageButton search_btn = (ImageButton) view.findViewById(R.id.search_club_member_by_id);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        GenericTypeIndicator<ArrayList<Customer>> members_type = new GenericTypeIndicator<ArrayList<Customer>>() {
                        };
                        members = dataSnapshot.child("ClubMembers").getValue(members_type);
                        if (members != null) {
                            for (Customer member : members) {
                                if (member.getId() == Integer.parseInt(ed.getText().toString())) {
                                    for (Table table : LoginActivity.restaurantManager.getTables()) {
                                        if (table.getNumber() == tableNum)
                                            table.setClubMember(true);
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

        return view;

    }

    public int getTableNum() {
        return tableNum;
    }

    public static void setTableNum(int tableNum1) {
        tableNum = tableNum1;
    }
}