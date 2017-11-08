package sapir_liran.melzarito.UI;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import Logic.RestaurantManager;
import sapir_liran.melzarito.R;

public class SearchClubMembersFragment extends Fragment {
    View view;
    public static int tableNum;
    private RestaurantManager restaurantManager = RestaurantManager.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.search_club_members_fragment, container, false);
        getActivity().setTitle(R.string.title_fragment_search_club_member);

        final EditText ed_club_member_id = (EditText) view.findViewById(R.id.club_member_id_text);

        ImageButton search_btn = (ImageButton) view.findViewById(R.id.search_club_member_by_id);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                restaurantManager.setClubMemberToTable(Integer.parseInt(ed_club_member_id.getText().toString()), tableNum, view.getContext());
            }
        });

        return view;

    }

    public static void setTableNum(int tableNum1) {
        tableNum = tableNum1;
    }
}