package sapir_liran.melzarito.UI;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import sapir_liran.melzarito.R;

public class AddNewClubMemberFragment extends Fragment {

    View view;
    FirebaseDatabase database ;
    DatabaseReference db ;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.add_new_club_members_fragment, container, false);
        getActivity().setTitle(R.string.title_fragment_add_new_club_member);

        database = FirebaseDatabase.getInstance();
        db = database.getReference();

//        db.child("ClubMembers").child("")
        return view;

    }


}