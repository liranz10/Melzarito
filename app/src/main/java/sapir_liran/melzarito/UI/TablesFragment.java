package sapir_liran.melzarito.UI;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import Logic.RestaurantManager;
import sapir_liran.melzarito.R;

public class TablesFragment extends android.app.Fragment {
    View view;
    Button tb;
    FragmentManager fragmentManager;
    OrderFragment orderFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       view= inflater.inflate(R.layout.tables_fragment, container, false);
        //--sapir: make the grid
       // restaurantManager = new RestaurantManager();
        orderFragment = new OrderFragment();
        tb = (Button)view.findViewById(R.id.table20);
        fragmentManager = getFragmentManager();

        tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, orderFragment ).addToBackStack(ClubMembersFragment.class.getName())
                        .commit();
            }
        });

        return view;

    }
}