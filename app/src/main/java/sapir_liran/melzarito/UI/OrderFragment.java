package sapir_liran.melzarito.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import Logic.RestaurantManager;
import sapir_liran.melzarito.R;

public class OrderFragment extends android.app.Fragment {
    View view;
    RestaurantManager restaurantManager = new RestaurantManager();
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       view= inflater.inflate(R.layout.order_fragment, container, false);
        Button btn;
        for(int i=0; i<restaurantManager.getMenu().getItems().size(); i++)
        {
            //need to add buttons with the text from the database
        }

        //add onClickListeners to the buttons

        return view;

    }
}