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

public class OrderFragment extends android.app.Fragment {
    private View view;
    private ItemsFragment fragment = new ItemsFragment();
    private FragmentManager fragmentManager;
    private int tableNum;

    public void setTableNum(int tableNum){
        this.tableNum = tableNum;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.order_fragment, container, false);
        fragment.setTableNum(tableNum);
        fragmentManager = getFragmentManager();
        getActivity().setTitle(R.string.title_fragment_menu);
        final Button appetizers_btn = (Button) view.findViewById(R.id.appetizers_btn);

        //go to appetizers menu
        appetizers_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.setCategoryName((String) appetizers_btn.getText());
                fragment.setCategory(view.getContext().getResources().getInteger(R.integer.appetizers_menu));
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment).addToBackStack(OrderFragment.class.getName())
                        .commit();
            }
        });

        final Button main_course_btn = (Button) view.findViewById(R.id.main_course_btn);

        //go to main course menu
        main_course_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.setCategoryName((String) main_course_btn.getText());
                fragment.setCategory(view.getContext().getResources().getInteger(R.integer.main_course_menu));
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment).addToBackStack(OrderFragment.class.getName())
                        .commit();
            }
        });

        final Button side_dishes_btn = (Button) view.findViewById(R.id.side_dishes_btn);

        //go to side dishes menu
        side_dishes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.setCategoryName((String) side_dishes_btn.getText());
                fragment.setCategory(view.getContext().getResources().getInteger(R.integer.side_dishes_menu));
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment).addToBackStack(OrderFragment.class.getName())
                        .commit();
            }
        });

        final Button specials_btn = (Button) view.findViewById(R.id.specials_btn);

        //go to specials menu
        specials_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.setCategoryName((String) specials_btn.getText());
                fragment.setCategory(view.getContext().getResources().getInteger(R.integer.specials_menu));
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment).addToBackStack(OrderFragment.class.getName())
                        .commit();
            }
        });

        final Button drinks_btn = (Button) view.findViewById(R.id.drinks_btn);

        //go to drinks menu
        drinks_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.setCategoryName((String) drinks_btn.getText());
                fragment.setCategory(view.getContext().getResources().getInteger(R.integer.drinks_menu));
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment).addToBackStack(OrderFragment.class.getName())
                        .commit();
            }
        });

        final Button deserts_btn = (Button) view.findViewById(R.id.deserts_btn);

        //go to deserts menu
        deserts_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.setCategoryName((String) deserts_btn.getText());
                fragment.setCategory(view.getContext().getResources().getInteger(R.integer.deserts_menu));
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment).addToBackStack(OrderFragment.class.getName())
                        .commit();
            }
        });
        return view;

    }
}