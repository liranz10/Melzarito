package sapir_liran.melzarito.UI;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import Logic.ClubMember;
import Logic.RestaurantManager;
import sapir_liran.melzarito.R;

public class AddNewClubMemberFragment extends Fragment {

    private View view;
    private ClubMember clubMember;
    private RestaurantManager restaurantManager = RestaurantManager.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.add_new_club_members_fragment, container, false);
        getActivity().setTitle(R.string.title_fragment_add_new_club_member);
        Button add_new_customer = (Button) view.findViewById(R.id.add_new_club_member_button);

        add_new_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readCustomerFromFragment();
                restaurantManager.writeClubMember(clubMember);
            }
        });
        return view;
    }


    private void readCustomerFromFragment() {
        EditText ed_id = (EditText) view.findViewById(R.id.club_member_id_text);
        int id = Integer.parseInt(ed_id.getText().toString());
        EditText ed_name = (EditText) view.findViewById(R.id.club_member_name_text);
        String name = ed_name.getText().toString();


            EditText ed_address = (EditText) view.findViewById(R.id.club_member_address_text);
            String address = ed_address.getText().toString();
            EditText ed_phone = (EditText) view.findViewById(R.id.club_member_phone_text);
            int phone = Integer.parseInt(ed_phone.getText().toString());

            clubMember = new ClubMember(id, name, address, phone);



    }


}