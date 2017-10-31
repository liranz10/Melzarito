package sapir_liran.melzarito.UI;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import Logic.Order;
import Logic.OrderItem;
import Logic.RestaurantManager;
import Logic.Table;
import sapir_liran.melzarito.R;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static sapir_liran.melzarito.R.drawable.note;

public class OpenOrdersFragment extends Fragment {
    private RelativeLayout fragment_layout;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.open_orders_fragment, container, false);
        fragment_layout=(RelativeLayout)view.findViewById(R.id.open_orders_layout);
        ArrayList<Order> openOrders =new ArrayList<>();
        openOrders.addAll(RestaurantManager.getOpenOrders());
        for(Order order : openOrders){
            TableLayout order_layout = new TableLayout(view.getContext());
            order_layout.setBackgroundResource(R.drawable.note);
            order_layout.setPadding(30,30,30,30);

            TableRow headline = new TableRow(view.getContext());
            TextView orderNum = new TextView(view.getContext());
            orderNum.setText("מספר הזמנה: "+order.getId());
            orderNum.setTextSize(20);
            headline.addView(orderNum);

            TableRow tableNum = new TableRow(view.getContext());
            TextView tableNumtext = new TextView(view.getContext());
            tableNumtext.setText("מספר שולחן: "+order.getId());
            tableNumtext.setTextSize(20);
            tableNum.addView(tableNumtext);

            TableRow item_headline = new TableRow(view.getContext());
            TextView items = new TextView(view.getContext());
            items.setText("פריטים בהזמנה:");
            items.setTextSize(20);
            item_headline.addView(items);
            TableRow item_row=new TableRow(view.getContext());
            for (OrderItem item : order.getOrderItems()){
                item_row = new TableRow(view.getContext());
                TextView item_text = new TextView(view.getContext());
                item_text.setTextSize(16);
                item_text.setText(item.getName());
                item_row.addView(item_text);


            }

            order_layout.addView(headline,new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            order_layout.addView(tableNum,new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            order_layout.addView(item_headline,new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            order_layout.addView(item_row,new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

            fragment_layout.addView(order_layout);


        }
        return view;

    }
}
