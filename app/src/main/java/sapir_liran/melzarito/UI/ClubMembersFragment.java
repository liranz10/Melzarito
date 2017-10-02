package sapir_liran.melzarito.UI;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import sapir_liran.melzarito.R;

public class ClubMembersFragment extends android.app.Fragment {
    View view;
    Button addNewMemberBtn,searchMemberBtn;
    FragmentManager fragmentManager;
    AddNewClubMemberFragment addClubfragment;
    SearchClubMembersFragment searchClubFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentManager = getFragmentManager();
        addClubfragment = new AddNewClubMemberFragment();
        searchClubFragment = new SearchClubMembersFragment();

       view= inflater.inflate(R.layout.club_members_fragment, container, false);

        addNewMemberBtn = (Button)view.findViewById(R.id.add_new_club_member);

        addNewMemberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, addClubfragment )
                        .commit();
            }
        });

        searchMemberBtn = (Button)view.findViewById(R.id.club_member_search);

        searchMemberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, searchClubFragment)
                        .commit();
            }
        });

        return view;

    }
}