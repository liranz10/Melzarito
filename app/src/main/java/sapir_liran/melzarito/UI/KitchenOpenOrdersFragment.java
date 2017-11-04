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
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import Logic.Order;
import Logic.OrderItem;
import Logic.RestaurantManager;
import sapir_liran.melzarito.R;

import static android.R.attr.button;
import static android.R.id.message;
//import static sapir_liran.melzarito.R.drawable.note;

public class KitchenOpenOrdersFragment extends Fragment {
    View view;
    private GridView fragment_layout;
    public ArrayList<Order> openOrders = new ArrayList<>();
    public OpenOrdersAdapter ordersAdapter;
    public int openOrdersNum = 0;
    FirebaseDatabase database;
    DatabaseReference db;
    private Button refresh_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.kitchen_open_orders_fragment, container, false);
        refresh_btn = (Button) view.findViewById(R.id.refresh_button);
        openOrders.clear();
        database = FirebaseDatabase.getInstance();
        db = database.getReference();


        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_layout = (GridView) view.findViewById(R.id.kitchen_open_orders_layout);
                openOrders.clear();
                openOrders.addAll(RestaurantManager.getOpenOrders());

                if (openOrders != null)
                    openOrdersNum = openOrders.size();
                ordersAdapter = new OpenOrdersAdapter(getActivity(), openOrdersNum);
                if(ordersAdapter!=null)
                    fragment_layout.setAdapter(ordersAdapter);
            }
        });


        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                openOrders.clear();
                RestaurantManager.orders.clear();
                if (dataSnapshot.child("Orders").getValue() != null) {
                    Integer[] ids = new Integer[(int) ((long) dataSnapshot.child("Orders").getChildrenCount())];
                    int i = 0;
                    for (DataSnapshot d : dataSnapshot.child("Orders").getChildren()) {
                        ids[i] = Integer.parseInt(d.getKey());
                        i++;
                    }
                    for (final Integer order_id : ids) {

                        GenericTypeIndicator<Order> order_type = new GenericTypeIndicator<Order>() {
                        };
                        final Order curr_order = dataSnapshot.child("Orders").child(order_id.toString()).getValue(order_type);
                        final Integer[] orderItemsIds = new Integer[(int) ((long) dataSnapshot.child("Orders").child(order_id + "").child("Order items").getChildrenCount())];
                        i = 0;
                        //if(orderItemsIds.length >0) {
                        for (DataSnapshot d : dataSnapshot.child("Orders").child(order_id + "").child("Order items").getChildren()) {
                            orderItemsIds[i] = Integer.parseInt(d.getKey());
                            i++;
                        }
                        //  }

                        final Query query = db.child("Orders").child(order_id + "").child("Order items");

                        query.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                if (dataSnapshot.getValue() != null) {
//
                                    try {
                                        GenericTypeIndicator<Date> date_type = new GenericTypeIndicator<Date>() {
                                        };
                                        Date tempLastModified = dataSnapshot.child("lastModifiedTime").getValue(date_type);
                                        String tempName = (String) dataSnapshot.child("name").getValue();
                                        int tempCategory = (int) ((long) dataSnapshot.child("category").getValue());
                                        int tempMenuId = (int) ((long) dataSnapshot.child("MenuItemId").getValue());
                                        int orderItem_id = Integer.parseInt(dataSnapshot.getKey());
                                        curr_order.addOrderItem(new OrderItem(tempMenuId, tempName, tempCategory, orderItem_id, tempLastModified, new ArrayList<String>()));
                                    } catch (NullPointerException ex) {
                                    }

//
                                }
                            }


                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        if (curr_order.isOpen()) {

                            RestaurantManager.orders.put(order_id, curr_order);
                        }


                        openOrders.addAll(RestaurantManager.orders.values());
                        //  tablesAdapter.notifyDataSetChanged();
                        //refresh_btn.callOnClick();

                    }
                }
                refresh_btn.callOnClick();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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

            order_layout.setBackgroundColor(Color.LTGRAY);
            TableLayout.LayoutParams tableRowParams =
                    new TableLayout.LayoutParams
                            (TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);

            TableRow headline = new TableRow(view.getContext());
            TextView orderNum = new TextView(view.getContext());

            orderNum.setText("מספר הזמנה: " + openOrders.get(position).getId());
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
            btns_row.addView(new ReadyButton(getContext()), new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            btns_row.addView(new OnPrepButton(getContext()), new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            btns_row.addView(new NotReadyButton(getContext()), new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            btns_row.addView(new ServiceButton(getContext()), new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));

            order_layout.addView(btns_row);


            return order_layout;
        }
    }

    class ReadyButton extends AppCompatButton {
        private OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TableLayout) ((TableRow) getParent()).getParent()).setBackgroundColor(Color.GREEN);
            }
        };

        public ReadyButton(Context context) {
            super(context);
            setText("מוכן");
            setBackgroundColor(Color.GREEN);
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            setLayoutParams(params);
            setGravity(Gravity.CENTER_HORIZONTAL);
            setOnClickListener(listener);
        }


    }

    class OnPrepButton extends AppCompatButton {
        private OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TableLayout) ((TableRow) getParent()).getParent()).setBackgroundColor(Color.YELLOW);
            }
        };

        public OnPrepButton(Context context) {
            super(context);
            setText("בהכנה");
            setBackgroundColor(Color.YELLOW);
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
           setLayoutParams(params);
          setGravity(Gravity.CENTER_HORIZONTAL);
            setOnClickListener(listener);


        }
    }

    class NotReadyButton extends AppCompatButton {
        private OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TableLayout) ((TableRow) getParent()).getParent()).setBackgroundColor(Color.LTGRAY);
            }
        };

        public NotReadyButton(Context context) {
            super(context);
            setText("לא מוכן");
            setBackgroundColor(Color.LTGRAY);
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            setLayoutParams(params);
            setGravity(Gravity.CENTER_HORIZONTAL);
            setOnClickListener(listener);
        }
    }
        class ServiceButton extends AppCompatButton {
            private OnClickListener listener = new OnClickListener() {
                @Override
                public void onClick(View v) {



                }
            };

            public ServiceButton(Context context) {
                super(context);

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



