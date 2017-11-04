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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.LinkedHashMap;

import Logic.Menu;
import Logic.MenuItem;
import Logic.Order;
import Logic.OrderItem;
import Logic.RestaurantManager;
import Logic.Table;
import sapir_liran.melzarito.R;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static sapir_liran.melzarito.R.id.order_layout;
//import static sapir_liran.melzarito.R.drawable.note;

public class OpenOrdersFragment extends Fragment {
    private  GridView fragment_layout;
    public  ArrayList<Order> openOrders = new ArrayList<>();
     View view;
    public  OpenOrdersAdapter ordersAdapter;
    public  int openOrdersNum = 0;
    FirebaseDatabase database ;
    DatabaseReference db ;
   // HashMap<Integer,Order> orders = new LinkedHashMap<>();
    //private boolean toRefresh = true;
    private Button refresh_btn;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.open_orders_fragment, container, false);
        refresh_btn = (Button)view.findViewById(R.id.refresh_button);

        database = FirebaseDatabase.getInstance();
        db = database.getReference();


        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_layout = (GridView) view.findViewById(R.id.open_orders_layout);
                openOrders.clear();
                openOrders.addAll(RestaurantManager.getOpenOrders());

                if(openOrders!=null)
                    openOrdersNum=openOrders.size();
                ordersAdapter = new OpenOrdersAdapter(getActivity(), openOrdersNum);

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

                        GenericTypeIndicator<Order> order_type= new GenericTypeIndicator<Order>(){};
                        final Order curr_order =dataSnapshot.child("Orders").child(order_id.toString()).getValue(order_type);
                        final Integer[] orderItemsIds = new Integer[(int)((long)dataSnapshot.child("Orders").child(order_id+"").child("Order items").getChildrenCount())];
                        i=0;
                        //if(orderItemsIds.length >0) {
                        for (DataSnapshot d : dataSnapshot.child("Orders").child(order_id + "").child("Order items").getChildren()) {
                            orderItemsIds[i] = Integer.parseInt(d.getKey());
                            i++;
                        }
                        //  }

                        final Query query = db.child("Orders").child(order_id+"").child("Order items");

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
                                    }catch (NullPointerException ex){}

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
            if (openOrders!=null && openOrders.get(position).isOpen()) {
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
//     public static void update(){
//         openOrders.clear();
//         openOrders.addAll(RestaurantManager.getOpenOrders());
//         view.refreshDrawableState();
//         tablesAdapter = new OpenOrdersAdapter(view.getContext(),openOrders.size());
//         fragment_layout.setAdapter(tablesAdapter);
//        }

/*sapir
    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("Orders").getValue() != null) {
                    Integer[] ids = new Integer[(int) ((long) dataSnapshot.child("Orders").getChildrenCount())];
                    int i = 0;
                    for (DataSnapshot d : dataSnapshot.child("Orders").getChildren()) {
                        ids[i] = Integer.parseInt(d.getKey());
                        i++;
                    }
                    for (final Integer order_id : ids) {

                        GenericTypeIndicator<Order> order_type= new GenericTypeIndicator<Order>(){};
                        final Order curr_order =dataSnapshot.child("Orders").child(order_id.toString()).getValue(order_type);
                        final Integer[] orderItemsIds = new Integer[(int)((long)dataSnapshot.child("Orders").child(order_id+"").child("Order items").getChildrenCount())];
                        i=0;
                        //if(orderItemsIds.length >0) {
                        for (DataSnapshot d : dataSnapshot.child("Orders").child(order_id + "").child("Order items").getChildren()) {
                            orderItemsIds[i] = Integer.parseInt(d.getKey());
                            i++;
                        }
                        //  }

                        final Query query = db.child("Orders").child(order_id+"").child("Order items");

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
                                        }catch (NullPointerException ex){}

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
                            orders.put(order_id, curr_order);
                        }



                        openOrders.addAll(orders.values());
                        tablesAdapter.notifyDataSetChanged();
                    }
                }
            }



        @Override
        public void onCancelled(DatabaseError error) {
        }
    };
sapir*/
}
