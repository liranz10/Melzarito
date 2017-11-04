package sapir_liran.melzarito.UI;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import Logic.Order;
import sapir_liran.melzarito.R;

public class TableOrderFragment extends android.app.Fragment {
    private View view;
    private  FloatingActionButton newOrderbtn;
    private OrderFragment orderFragment;
    private FragmentManager fragmentManager;
    private int tableNumber;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.table_order_fragment, container, false);

        newOrderbtn = (FloatingActionButton)view.findViewById(R.id.new_order) ;
        fragmentManager = getFragmentManager();
        getActivity().setTitle(R.string.title_fragment_table_total_order);
        newOrderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderFragment=new OrderFragment();
                LoginActivity.restaurantManager.CreateNewOrderAndWriteToDB(tableNumber);
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, orderFragment ).addToBackStack(TableOrderFragment.class.getName())
                        .commit();

            }
        });


        return view;

    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }
}