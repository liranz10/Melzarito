package sapir_liran.melzarito.UI;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import Logic.Order;
import Logic.OrderItem;
import Logic.RestaurantManager;
import sapir_liran.melzarito.R;

public class OpenOrdersFragment extends Fragment {
    private GridView fragment_layout;
    public ArrayList<Order> openOrders = new ArrayList<>();
    View view;
    public OpenOrdersAdapter ordersAdapter;
    public int openOrdersNum = 0;
    RestaurantManager restaurantManager = RestaurantManager.getInstance();


    private Button refresh_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.open_orders_fragment, container, false);
        refresh_btn = (Button) view.findViewById(R.id.refresh_button);

        // refresh view
        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_layout = (GridView) view.findViewById(R.id.open_orders_layout);
                openOrders.clear();
                openOrders.addAll(restaurantManager.getOrders());

                if (openOrders != null)
                    openOrdersNum = openOrders.size();
                ordersAdapter = new OpenOrdersAdapter(getActivity(), openOrdersNum);
                fragment_layout.setAdapter(ordersAdapter);
            }
        });


        refresh_btn.callOnClick();


        return view;
    }

    class OpenOrdersAdapter extends BaseAdapter {

        private final Context mContext;
        private int size;

        public OpenOrdersAdapter(Context context, int size) {
            this.mContext = context;
            this.size = size;
        }

        @Override
        public int getCount() {
            return this.size;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        //view open order
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (openOrders != null && openOrders.get(position).isOpen()) {
                TableLayout order_layout = new TableLayout(mContext);
                order_layout.setBackgroundResource(R.drawable.note);
                TableLayout.LayoutParams tableRowParams =
                        new TableLayout.LayoutParams
                                (TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
                TableRow headline = new TableRow(view.getContext());
                TextView orderNum = new TextView(view.getContext());
                orderNum.setText("\n\n" + view.getContext().getString(R.string.order_num) + openOrders.get(position).getId());
                orderNum.setTextSize(16);

                headline.addView(orderNum);

                TableRow tableNum = new TableRow(view.getContext());
                tableNum.setGravity(Gravity.CENTER);
                TextView tableNumtext = new TextView(view.getContext());
                tableNumtext.setText(view.getContext().getString(R.string.table_num) + openOrders.get(position).getTableNumber());
                tableNumtext.setTextSize(22);

                tableNum.addView(tableNumtext);

                TableRow item_headline = new TableRow(view.getContext());
                TextView items = new TextView(view.getContext());
                items.setText(view.getContext().getString(R.string.items_in_order));
                items.setTextSize(16);

                item_headline.addView(items);

                headline.setLayoutParams(tableRowParams);

                order_layout.addView(headline, tableRowParams);
                order_layout.addView(tableNum, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

                tableRowParams.setMargins(0, 0, 50, 0);
                item_headline.setLayoutParams(tableRowParams);
                order_layout.addView(item_headline, tableRowParams);

                for (OrderItem item : openOrders.get(position).getOrderItems()) {
                    TableRow item_row = new TableRow(view.getContext());
                    TextView item_text = new TextView(view.getContext());
                    item_text.setTextSize(13);
                    item_text.setText(item.getName());
                    item_row.addView(item_text);
                    tableRowParams.setMargins(0, 0, 50, 20);
                    item_row.setLayoutParams(tableRowParams);

                    order_layout.addView(item_row, tableRowParams);
                }
                return order_layout;
            } else return null;
        }
    }
}
