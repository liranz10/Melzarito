package sapir_liran.melzarito.UI;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import Logic.RestaurantManager;
import sapir_liran.melzarito.R;

public class AddSpecialFragment extends Fragment {

    private View view;
    private RestaurantManager restaurantManager = RestaurantManager.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.add_new_special_fragment, container, false);
        getActivity().setTitle(R.string.title_fragment_add_new_special);
        Button add_btn =(Button) view.findViewById(R.id.add_special_button);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText special_name =(EditText)view.findViewById(R.id.special_name);
                String name = special_name.getText().toString();
                restaurantManager.addNewSpecial(name);
            }
        });
        return view;

    }


}
