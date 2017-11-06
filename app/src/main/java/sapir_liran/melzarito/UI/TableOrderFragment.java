package sapir_liran.melzarito.UI;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;

import Logic.Order;
import Logic.OrderItem;
import Logic.RestaurantManager;
import sapir_liran.melzarito.R;

public class TableOrderFragment extends android.app.Fragment {
    private View view;
    private  FloatingActionButton newOrderbtn;
    private  FloatingActionButton setClubMember_btn;
    private OrderFragment orderFragment;
    private SearchClubMembersFragment searchClubMembersFragment;
    private FragmentManager fragmentManager;
    private int tableNumber;
    private ListView listView;
    ArrayList<Order> ordersList = new ArrayList<>();
    FirebaseDatabase database ;
    DatabaseReference db ;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.table_order_fragment, container, false);

        newOrderbtn = (FloatingActionButton)view.findViewById(R.id.new_order) ;
        fragmentManager = getFragmentManager();
        getActivity().setTitle(R.string.title_fragment_table_total_order);
        newOrderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderFragment=new OrderFragment();
                LoginActivity.restaurantManager.CreateNewOrderAndWriteToDB(tableNumber);
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, orderFragment ).addToBackStack(TableOrderFragment.class.getName())
                        .commit();

            }
        });

        setClubMember_btn = (FloatingActionButton)view.findViewById(R.id.set_club_member) ;
        setClubMember_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchClubMembersFragment = new SearchClubMembersFragment();
                SearchClubMembersFragment.setTableNum(tableNumber);
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, searchClubMembersFragment).addToBackStack(SearchClubMembersFragment.class.getName())
                        .commit();
            }
        });


        listView = (ListView)view.findViewById(R.id.listView);
//        ordersList.addAll(RestaurantManager.orders.values());


        ordersList.clear();
        ordersList.addAll(LoginActivity.restaurantManager.getOrders());

        TableOrderAdapter tableOrderAdapter = new TableOrderAdapter(getActivity(), ordersList.size());
        listView.setAdapter(tableOrderAdapter);
//        for (Order order : coll_orders){
//            if(order.getTableNumber() == tableNumber){
//                TableOrderAdapter tableOrderAdapter = new TableOrderAdapter(getActivity(), order.getOrderItems().size());
//                listView.setAdapter(tableOrderAdapter);
//                for (OrderItem orderItem : order.getOrderItems()) {
//                    TextView tv = new TextView(view.getContext());
//                    tv.setText(orderItem.getName());
//                    listView.addView(tv);
//                }
//            }
//        }


        return view;

    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }


    class TableOrderAdapter extends BaseAdapter {

        private final Context mContext;
        private int size;

        // 1
        public TableOrderAdapter(Context context, int size) {
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
            if(ordersList.get(position).getTableNumber() == tableNumber){
                TableLayout tableLayout = new TableLayout(mContext);
                TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
                tableLayoutParams.setMargins(0,20,50,0);
                for (OrderItem orderItem : ordersList.get(position).getOrderItems()) {
                    TableRow tr = new TableRow(view.getContext());
                    TextView tv = new TextView(view.getContext());
                    tv.setText(orderItem.getName());
                    tv.setTextSize(20);
                    tr.addView(tv);
                    tableLayout.addView(tr, tableLayoutParams);
                }
                return tableLayout;
            }
            else {
                TextView nothing = new TextView(getContext());
                return nothing;
            }
        }

    }
}