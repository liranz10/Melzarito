package sapir_liran.melzarito.UI;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import Logic.Menu;
import Logic.MenuItem;
import Logic.RestaurantManager;
import sapir_liran.melzarito.R;

public class ItemsFragment extends android.app.Fragment {
    private View view;
    private int category;
    private String categoryName;
    private TableLayout layout;
    private FragmentManager fragmentManager;
    private RestaurantManager restaurantManager = RestaurantManager.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.items_fragment, container, false);
        getActivity().setTitle(categoryName);

        layout = (TableLayout) view.findViewById(R.id.items_layout);
        if (layout.getChildCount() != 0)
            layout.removeAllViews();
        //get menu
        final Menu menu = restaurantManager.getMenu();
        for (MenuItem item : menu.getItems()) {
            //view all items from the selected category
            if (item != null && item.getCategory() == category) {

                TableRow tr = new TableRow(getActivity());
                tr.setGravity(Gravity.CENTER);
                TextView item_view = new TextView(view.getContext());
                item_view.setTextSize(24);
                //view item name
                item_view.setText(item.getName());
                Button pls_button = new Button(view.getContext());
                TableRow.LayoutParams lyt = new TableRow.LayoutParams(100, 100);
                pls_button.setLayoutParams(lyt);
                pls_button.setBackgroundResource(R.drawable.plus_order);
                Button minus_button = new Button(view.getContext());
                minus_button.setLayoutParams(lyt);
                minus_button.setBackgroundResource(R.drawable.minus);
                //quantity picker
                final TextView quantity = new TextView(view.getContext());
                quantity.setTextSize(24);
                quantity.setText("0");
                tr.setBackgroundResource(R.drawable.buttonshape);
                //quantity up
                pls_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int curr = Integer.parseInt(quantity.getText().toString());
                        curr++;
                        quantity.setText(curr + "");
                    }
                });
                //quantity down
                minus_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!quantity.getText().equals("0")) {
                            int curr = Integer.parseInt(quantity.getText().toString());
                            curr--;
                            quantity.setText(curr + "");
                        }
                    }
                });

                item_view.setPadding(60, 0, 30, 0);
                quantity.setPadding(10, 0, 10, 0);
                //set id to identified later
                quantity.setId(item.getId());
                //adding views
                tr.addView(item_view);
                tr.addView(pls_button);
                tr.addView(quantity);
                tr.addView(minus_button);
                layout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            }
        }

        Button add_items_to_order_btn = (Button) view.findViewById(R.id.add_items_to_order);
        //adding items to order
        add_items_to_order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (MenuItem i : menu.getItemsOnCategory(category)) {
                    //find matching edit_text
                    int text_item_id = getResources().getIdentifier(String.valueOf(i.getId()), "id", "com.sapir_liran.melzarito.UI");
                    TextView quantity = (TextView) view.findViewById(text_item_id);
                    for (int j = 0; j < Integer.parseInt(quantity.getText().toString()); j++) {
                        restaurantManager.createOrderItemAndWriteToDB(i, category);
                    }
                }
                //return back screen
                fragmentManager = getFragmentManager();
                fragmentManager.popBackStack();
            }
        });
        return view;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}