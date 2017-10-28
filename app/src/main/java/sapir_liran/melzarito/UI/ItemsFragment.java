package sapir_liran.melzarito.UI;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import Logic.Menu;
import Logic.MenuItem;
import Logic.OrderItem;
import Logic.RestaurantManager;
import Logic.Table;
import sapir_liran.melzarito.R;

import static android.R.attr.button;
import static android.os.Build.VERSION_CODES.M;

public class ItemsFragment extends android.app.Fragment {
    View view;
    private int category;
    private String text;
    private TableLayout layout;


    // private ArrayList<Button> itemsBtnArray = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.items_fragment, container, false);
        getActivity().setTitle(text);
         layout = (TableLayout) view.findViewById(R.id.items_layout);
        if (layout.getChildCount()!=0)
            layout.removeAllViews();
        final Menu menu = LoginActivity.restaurantManager.getMenu();
        for(MenuItem item : menu.getItems()){
            if (item.getCategory() == category)
            {

                TableRow tr = new TableRow(getActivity());
                tr.setGravity(Gravity.CENTER);
                TextView item_view = new TextView(view.getContext());
                item_view.setTextSize(24);
                item_view.setText(item.getName());

                Button pls_button = new Button(view.getContext());
                TableRow.LayoutParams lyt = new TableRow.LayoutParams(100, 100);

                pls_button.setLayoutParams(lyt);

                pls_button.setBackgroundResource(R.drawable.plus_order);

                Button minus_button = new Button(view.getContext());
                minus_button.setLayoutParams(lyt);

                minus_button.setBackgroundResource(R.drawable.minus);

                final TextView quantity = new TextView(view.getContext());
                quantity.setTextSize(24);
                quantity.setText("0");
                tr.setBackgroundResource(R.drawable.buttonshape);
                pls_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int curr = Integer.parseInt(quantity.getText().toString());
                    curr++;
                    quantity.setText(curr + "");
                }
            });

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

                item_view.setPadding(60,0,30,0);
                quantity.setPadding(10,0,10,0);
                quantity.setId(item.getId());
                tr.addView(item_view);
                tr.addView(pls_button);
                tr.addView(quantity);
                tr.addView(minus_button);
                layout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

//                layout.addView(tr);
            }

        }

        Button add_items_to_order_btn = (Button)view.findViewById(R.id.add_items_to_order);

        add_items_to_order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                for (MenuItem i : menu.getItemsOnCategory(category))
                {

                        int idd = getResources().getIdentifier(String.valueOf(i.getId()), "id", "com.sapir_liran.melzarito.UI");
                        TextView quantity = (TextView)view.findViewById(idd);
                        for (int j=0 ; j < Integer.parseInt(quantity.getText().toString()) ; j++ ){
                            LoginActivity.restaurantManager.createOrderItemAndWriteToDB(i,category);
                        }


                }

            }
        });

        return view;

    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setText(String text) {
        this.text = text;
    }


}