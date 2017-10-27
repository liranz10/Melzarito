package sapir_liran.melzarito.UI;

import android.app.FragmentManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TableRow;

import Logic.RestaurantManager;
import Logic.Table;
import sapir_liran.melzarito.R;

public class TablesFragment extends android.app.Fragment {
    View view;
    GridLayout gridLayout;
    FragmentManager fragmentManager;
    TableOrderFragment tableOrderFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       view= inflater.inflate(R.layout.tables_fragment, container, false);
        //--sapir: make the grid
       // restaurantManager = new RestaurantManager();
        tableOrderFragment = new TableOrderFragment();
        gridLayout = (GridLayout) view.findViewById(R.id.tables_layout);
        fragmentManager = getFragmentManager();

        for(Table table : LoginActivity.restaurantManager.getTables()){
            Button table_button = (Button)new Button(getActivity());
            table_button.setGravity(Gravity.CENTER);
            table_button.setId(table.getNumber());
            LinearLayout.LayoutParams lyt = new LinearLayout.LayoutParams(300, 300);
            table_button.setLayoutParams(lyt);
            table_button.setBackgroundResource(R.drawable.buttonshape);
            Drawable img = getActivity().getResources().getDrawable(R.drawable.table_image, null);
            table_button.setCompoundDrawablesWithIntrinsicBounds( null, null, null, img);
            table_button.setTextSize(30);
            table_button.setText(table.getNumber() + "");
            table_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, tableOrderFragment ).addToBackStack(TablesFragment.class.getName())
                            .commit();
                }
            });
            gridLayout.addView(table_button);
        }


        return view;

    }
}