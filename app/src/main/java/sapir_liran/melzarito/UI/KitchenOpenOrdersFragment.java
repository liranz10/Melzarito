package sapir_liran.melzarito.UI;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
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

public class KitchenOpenOrdersFragment extends Fragment {
    View view;
    private GridView fragment_layout;
    public ArrayList<Order> openOrders = new ArrayList<>();
    public OpenOrdersAdapter ordersAdapter;
    public int openOrdersNum = 0;
    RestaurantManager restaurantManager = RestaurantManager.getInstance();
    private Button refresh_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.kitchen_open_orders_fragment, container, false);
        refresh_btn = (Button) view.findViewById(R.id.refresh_button);
        openOrders.clear();

        // refresh view
        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_layout = (GridView) view.findViewById(R.id.kitchen_open_orders_layout);
                openOrders.clear();
                openOrders.addAll(restaurantManager.getOrders());

                if (openOrders != null)
                    openOrdersNum = openOrders.size();
                ordersAdapter = new OpenOrdersAdapter(getActivity(), openOrdersNum);
                if (ordersAdapter != null)
                    fragment_layout.setAdapter(ordersAdapter);
            }
        });


        restaurantManager.getNotificationCounter();

        // get all open orders from DB
        restaurantManager.loadAllOpenOrders();
        refresh_btn.callOnClick();

        return view;
    }

    // grid view adapter
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

        //view open order and order items
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            TableLayout order_layout = new TableLayout(mContext);
            //change color status color
            if (openOrders.get(position).getStatus() == getContext().getResources().getInteger(R.integer.notready))
                order_layout.setBackgroundColor(Color.LTGRAY);
            else if (openOrders.get(position).getStatus() == getContext().getResources().getInteger(R.integer.onprep))
                order_layout.setBackgroundColor(Color.YELLOW);
            else if (openOrders.get(position).getStatus() == getContext().getResources().getInteger(R.integer.ready))
                order_layout.setBackgroundColor(Color.GREEN);

            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
            TableRow headline = new TableRow(view.getContext());
            TextView orderNum = new TextView(view.getContext());
            orderNum.setText(getContext().getResources().getString(R.string.order_num) + openOrders.get(position).getId());
            orderNum.setTextSize(16);

            headline.addView(orderNum);

            TableRow tableNum = new TableRow(view.getContext());
            tableNum.setGravity(Gravity.CENTER);
            TextView tableNumtext = new TextView(view.getContext());
            tableNumtext.setText(getContext().getResources().getString(R.string.table_num) + openOrders.get(position).getTableNumber());
            tableNumtext.setTextSize(22);

            tableNum.addView(tableNumtext);

            TableRow item_headline = new TableRow(view.getContext());
            TextView items = new TextView(view.getContext());
            items.setText(getContext().getResources().getString(R.string.items_in_order));
            items.setTextSize(16);

            item_headline.addView(items);

            tableRowParams.setMargins(0, 50, 0, 100);
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

            TableRow btns_row = new TableRow(view.getContext());
            //status and service buttons
            btns_row.addView(new ReadyButton(getContext(), openOrders.get(position).getId()), new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            btns_row.addView(new OnPrepButton(getContext(), openOrders.get(position).getId()), new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            btns_row.addView(new NotReadyButton(getContext(), openOrders.get(position).getId()), new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            btns_row.addView(new ServiceButton(getContext(), openOrders.get(position).getId(), openOrders.get(position).getTableNumber()), new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));

            order_layout.addView(btns_row);

            return order_layout;
        }
    }

    class ReadyButton extends AppCompatButton {
        private int orderId;
        private final int STATUS = 3;
        private OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TableLayout) ((TableRow) getParent()).getParent()).setBackgroundColor(Color.GREEN);
                restaurantManager.changeOrderStatus(orderId, STATUS);
            }
        };

        public ReadyButton(Context context, int orderId) {
            super(context);
            this.orderId = orderId;
            setText(getContext().getResources().getString(R.string.ready_button_text));
            setBackgroundColor(Color.GREEN);

            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            setLayoutParams(params);
            setGravity(Gravity.CENTER_HORIZONTAL);
            setOnClickListener(listener);
        }


    }

    class OnPrepButton extends AppCompatButton {
        private int orderId;
        private final int STATUS = 2;

        private OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TableLayout) ((TableRow) getParent()).getParent()).setBackgroundColor(Color.YELLOW);
                restaurantManager.changeOrderStatus(orderId, STATUS);

            }
        };

        public OnPrepButton(Context context, int orderId) {
            super(context);
            this.orderId = orderId;
            setText(R.string.on_prep_btn_txt);
            setBackgroundColor(Color.YELLOW);
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            setLayoutParams(params);
            setGravity(Gravity.CENTER_HORIZONTAL);
            setOnClickListener(listener);


        }
    }

    class NotReadyButton extends AppCompatButton {
        private int orderId;
        private final int STATUS = 1;
        private OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TableLayout) ((TableRow) getParent()).getParent()).setBackgroundColor(Color.LTGRAY);
                restaurantManager.changeOrderStatus(orderId, STATUS);


            }
        };

        public NotReadyButton(Context context, int orderId) {
            super(context);
            this.orderId = orderId;
            setText(R.string.not_ready_btn_txt);
            setBackgroundColor(Color.LTGRAY);
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            setLayoutParams(params);
            setGravity(Gravity.CENTER_HORIZONTAL);
            setOnClickListener(listener);
        }
    }

    class ServiceButton extends AppCompatButton {
        private int orderId;
        private int tableNumber;
        private OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                restaurantManager.sendServiceNotification(orderId, tableNumber);
            }
        };

        public ServiceButton(Context context, int orderId, int tableNumber) {
            super(context);
            this.orderId = orderId;
            this.tableNumber = tableNumber;
            Drawable img = context.getDrawable(R.drawable.servicebell);
            setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            setLayoutParams(params);
            setGravity(Gravity.CENTER_HORIZONTAL);
            setOnClickListener(listener);
        }
    }

}



