package sapir_liran.melzarito.UI;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import sapir_liran.melzarito.R;

public class SearchClubMembersFragment extends Fragment {
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.search_club_members_fragment, container, false);
        getActivity().setTitle(R.string.title_fragment_search_club_member);
        return view;

    }
}