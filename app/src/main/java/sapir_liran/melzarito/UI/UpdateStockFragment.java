package sapir_liran.melzarito.UI;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import Logic.Order;
import sapir_liran.melzarito.R;

import static android.R.attr.fragment;

public class UpdateStockFragment extends android.app.Fragment {
    View view;
    UpdateStockItemsFragment fragment = new UpdateStockItemsFragment();
    private FragmentManager fragmentManager;
    private Order order;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.update_stock_fragment, container, false);
        getActivity().setTitle(R.string.title_update_stocks);

        fragmentManager = getFragmentManager();

        final Button appetizers_btn =(Button) view.findViewById(R.id.appetizers_btn);
        appetizers_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.setText((String)appetizers_btn.getText());
                fragment.setCategory(1);
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame_kitchen, fragment).addToBackStack(UpdateStockFragment.class.getName())
                        .commit();
            }
        });

        final Button main_course_btn =(Button) view.findViewById(R.id.main_course_btn);
        main_course_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.setText((String)main_course_btn.getText());
                fragment.setCategory(2);
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame_kitchen, fragment).addToBackStack(UpdateStockFragment.class.getName())
                        .commit();
            }
        });

        final Button side_dishes_btn =(Button) view.findViewById(R.id.side_dishes_btn);
        side_dishes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.setText((String)side_dishes_btn.getText());
                fragment.setCategory(3);
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame_kitchen, fragment).addToBackStack(UpdateStockFragment.class.getName())
                        .commit();
            }
        });

        final Button specials_btn =(Button) view.findViewById(R.id.specials_btn);
        specials_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.setText((String)specials_btn.getText());
                fragment.setCategory(4);
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame_kitchen, fragment).addToBackStack(UpdateStockFragment.class.getName())
                        .commit();
            }
        });

        final Button drinks_btn =(Button) view.findViewById(R.id.drinks_btn);
        drinks_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.setText((String)drinks_btn.getText());
                fragment.setCategory(5);
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame_kitchen, fragment).addToBackStack(UpdateStockFragment.class.getName())
                        .commit();
            }
        });

        final Button deserts_btn =(Button) view.findViewById(R.id.deserts_btn);
        deserts_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.setText((String)deserts_btn.getText());
                fragment.setCategory(6);
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame_kitchen, fragment).addToBackStack(UpdateStockFragment.class.getName())
                        .commit();
            }
        });
        return view;

    }
}