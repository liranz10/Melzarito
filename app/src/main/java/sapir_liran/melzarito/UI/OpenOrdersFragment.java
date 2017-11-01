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
//import static sapir_liran.melzarito.R.drawable.note;

public class OpenOrdersFragment extends Fragment {
    private GridView fragment_layout;
    ArrayList<Order> openOrders = new ArrayList<>();
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.open_orders_fragment, container, false);
        fragment_layout = (GridView) view.findViewById(R.id.open_orders_layout);
        openOrders.addAll(RestaurantManager.getOpenOrders());
        OpenOrdersAdapter tablesAdapter = new OpenOrdersAdapter(getActivity(), openOrders.size());
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
            TableLayout order_layout = new TableLayout(mContext);

            // order_layout.setBackgroundResource(R.drawable.note);

            TableRow headline = new TableRow(view.getContext());

            TextView orderNum = new TextView(view.getContext());
            orderNum.setText("מספר הזמנה: " + openOrders.get(position).getId());
            orderNum.setTextSize(16);
            headline.addView(orderNum);

            TableRow tableNum = new TableRow(view.getContext());

            TextView tableNumtext = new TextView(view.getContext());
            tableNumtext.setText("מספר שולחן: " + openOrders.get(position).getTableNumber());
            tableNumtext.setTextSize(16);
            tableNum.addView(tableNumtext);

            TableRow item_headline = new TableRow(view.getContext());

            TextView items = new TextView(view.getContext());
            items.setText("פריטים בהזמנה:");
            items.setTextSize(16);
            item_headline.addView(items);

            TableRow item_row = new TableRow(view.getContext());

            for (OrderItem item : openOrders.get(position).getOrderItems()) {
                item_row = new TableRow(view.getContext());
                TextView item_text = new TextView(view.getContext());
                item_text.setTextSize(13);
                item_text.setText(item.getName());
                item_row.addView(item_text);
            }

            order_layout.addView(headline, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            order_layout.addView(tableNum, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            order_layout.addView(item_headline, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
            order_layout.addView(item_row, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

            return order_layout;
        }
    }
}
