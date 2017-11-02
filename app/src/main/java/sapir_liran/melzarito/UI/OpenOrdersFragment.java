package sapir_liran.melzarito.UI;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
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
import static sapir_liran.melzarito.R.id.order_layout;
//import static sapir_liran.melzarito.R.drawable.note;

public class OpenOrdersFragment extends Fragment {
    private GridView fragment_layout;
    ArrayList<Order> openOrders = new ArrayList<>();
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.open_orders_fragment, container, false);
        fragment_layout = (GridView) view.findViewById(R.id.open_orders_layout);
        openOrders.clear();
        openOrders.addAll(RestaurantManager.getOpenOrders());

        int openOrdersNum = 0;
        if(openOrders!=null)
            openOrdersNum=openOrders.size();
        OpenOrdersAdapter tablesAdapter = new OpenOrdersAdapter(getActivity(), openOrdersNum);
        fragment_layout.setAdapter(tablesAdapter);

        return view;
    }

    class OpenOrdersAdapter extends BaseAdapter {

        private final Context mContext;
        private int size;

        // 1
        public OpenOrdersAdapter(Context context, int size) {
            this.mContext = context;
            this.size = size;
        }

        // 2
        @Override
        public int getCount() {
            return this.size;
        }

        // 3
        @Override
        public long getItemId(int position) {
            return 0;
        }

        // 4
        @Override
        public Object getItem(int position) {
            return null;
        }

        // 5
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (openOrders!=null) {
                TableLayout order_layout = new TableLayout(mContext);
                order_layout.setBackgroundResource(R.drawable.note);
                TableLayout.LayoutParams tableRowParams =
                        new TableLayout.LayoutParams
                                (TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);

                TableRow headline = new TableRow(view.getContext());
                TextView orderNum = new TextView(view.getContext());

                orderNum.setText("\n\nמספר הזמנה: " + openOrders.get(position).getId());
                orderNum.setTextSize(16);
                headline.addView(orderNum);

                TableRow tableNum = new TableRow(view.getContext());
                tableNum.setGravity(Gravity.CENTER);
                TextView tableNumtext = new TextView(view.getContext());
                tableNumtext.setText("מספר שולחן: " + openOrders.get(position).getTableNumber());
                tableNumtext.setTextSize(22);
                tableNum.addView(tableNumtext);

                TableRow item_headline = new TableRow(view.getContext());

                TextView items = new TextView(view.getContext());
                items.setText("פריטים בהזמנה:");
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
            }
            else return null;
        }
    }
}
