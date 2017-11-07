package sapir_liran.melzarito.UI;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import Logic.Order;
import Logic.OrderItem;
import Logic.RestaurantManager;
import sapir_liran.melzarito.R;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class TableOrderFragment extends android.app.Fragment {
    private View view;
    private FloatingActionButton newOrderbtn;
    private FloatingActionButton setClubMember_btn;
    private OrderFragment orderFragment;
    private SearchClubMembersFragment searchClubMembersFragment;
    private FragmentManager fragmentManager;
    private int tableNumber;
    private ListView listView;
    private ArrayList<Order> ordersList = new ArrayList<>();
    private RestaurantManager restaurantManager = RestaurantManager.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.table_order_fragment, container, false);

        newOrderbtn = (FloatingActionButton) view.findViewById(R.id.new_order);
        fragmentManager = getFragmentManager();
        getActivity().setTitle(R.string.title_fragment_table_total_order);

        //add new order
        newOrderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderFragment = new OrderFragment();
                restaurantManager.CreateNewOrderAndWriteToDB(tableNumber);
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, orderFragment).addToBackStack(TableOrderFragment.class.getName())
                        .commit();

            }
        });

        setClubMember_btn = (FloatingActionButton) view.findViewById(R.id.set_club_member);

        //set club member
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


        listView = (ListView) view.findViewById(R.id.listView);

        ordersList.clear();
        ordersList.addAll(restaurantManager.getOrders());

        TableOrderAdapter tableOrderAdapter = new TableOrderAdapter(getActivity(), ordersList.size());
        listView.setAdapter(tableOrderAdapter);

        return view;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }


    class TableOrderAdapter extends BaseAdapter {

        private final Context mContext;
        private int size;

        public TableOrderAdapter(Context context, int size) {
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

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TableLayout tableLayout = new TableLayout(mContext);
            tableLayout.setColumnStretchable(1,true);
            if (ordersList.get(position).getTableNumber() == tableNumber) {
                TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
                tableLayoutParams.setMargins(0, 20, 50, 0);
                for (OrderItem orderItem : ordersList.get(position).getOrderItems()) {
                    TableRow tr = new TableRow(view.getContext());
                    TextView item_name = new TextView(view.getContext());
                    item_name.setText(orderItem.getName());
                    item_name.setTextSize(20);
                    //item_name.setPadding(400,0,0,0);
                    TextView price = new TextView(view.getContext());
                    price.setText(orderItem.getPrice()+"");
                    price.setTextSize(20);

//                    item_name.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 0.7f));
//                    price.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 0.3f));

                    tr.addView(item_name);
                    tr.addView(price);
                    tableLayout.addView(tr, tableLayoutParams);
                }
            }
            return tableLayout;
        }

    }
}