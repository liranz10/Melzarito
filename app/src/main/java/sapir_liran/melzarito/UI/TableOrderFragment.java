package sapir_liran.melzarito.UI;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.print.PrintManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.print.PrintHelper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import Logic.Order;
import Logic.OrderItem;
import Logic.RestaurantManager;
import Logic.Table;
import sapir_liran.melzarito.R;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class TableOrderFragment extends android.app.Fragment {
    private View view;
    private FloatingActionButton newOrderbtn;
    private FloatingActionButton setClubMember_btn;
    private FloatingActionButton print_btn;
    private FloatingActionButton coins_btn;
    private TextView total_payment_tv;

    private OrderFragment orderFragment;
    private SearchClubMembersFragment searchClubMembersFragment;
    private FragmentManager fragmentManager;
    private int tableNumber;
    private ListView listView;
    private ArrayList<Order> ordersList = new ArrayList<>();
    private RestaurantManager restaurantManager = RestaurantManager.getInstance();
    private double totalPayment = 0;
    private boolean isClubMember = false;


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
                orderFragment.setTableNum(tableNumber);
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
                searchClubMembersFragment.setTableNum(tableNumber);
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, searchClubMembersFragment).addToBackStack(SearchClubMembersFragment.class.getName())
                        .commit();
            }
        });
        print_btn = (FloatingActionButton) view.findViewById(R.id.print_bill);
        print_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get a PrintManager instance
                PrintManager printManager = (PrintManager) getActivity()
                        .getSystemService(Context.PRINT_SERVICE);

                // Set job name, which will be displayed in the print queue
                String jobName = getActivity().getString(R.string.app_name) + " Document";

                // Start a print job, passing in a PrintDocumentAdapter implementation
                // to handle the generation of a print document
                printManager.print(jobName, new MyPrintDocumentAdapter(),
                        null); //

            }
        });

        coins_btn = (FloatingActionButton)view.findViewById(R.id.coins);
        coins_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Order order : restaurantManager.getOrders()){
                    if (order.getTableNumber() == tableNumber){
                        order.setOpen(false);
                        //change in DB
                        restaurantManager.closeOrder(order.getId());
                    }
                }

                totalPayment = 0;
                total_payment_tv.setText("סה\"כ לתשלום: " + new DecimalFormat("##.##").format(totalPayment));
//                listView.removeAllViews();
                listView.setAdapter(null);
                for(int i=0; i < restaurantManager.getTables().size(); i++){
                    if (restaurantManager.getTables().get(i).getNumber() == tableNumber){
                        restaurantManager.getTables().get(i).setClubMember(false);
                        restaurantManager.getTables().get(i).setIsEmpty(true);
                        //change in db
                        restaurantManager.emptyTable(i);
                    }
                }
            }
        });


        listView = (ListView) view.findViewById(R.id.listView);

        ordersList.clear();
        ordersList.addAll(restaurantManager.getOrders());

        totalPayment = 0;
        TableOrderAdapter tableOrderAdapter = new TableOrderAdapter(getActivity(), ordersList.size());
        listView.setAdapter(tableOrderAdapter);

        // check if the customer is club member
        for(Table table : restaurantManager.getTables()) {
            if ((table.getNumber() == tableNumber) && (table.isClubMember()))
                isClubMember = true;
        }

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
            tableLayout.setColumnStretchable(0,true);
            if (ordersList.get(position).getTableNumber() == tableNumber) {

                TableRow.LayoutParams tableRowPriceParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                tableRowPriceParams.setMargins(25,0,0,0);

                TableRow.LayoutParams tableRowItemNameParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                tableRowItemNameParams.setMargins(0,0,25,0);

                for (OrderItem orderItem : ordersList.get(position).getOrderItems()) {
                    TableRow tr = new TableRow(view.getContext());
                    TextView item_name = new TextView(view.getContext());
                    item_name.setText(orderItem.getName());
                    item_name.setTextSize(18);
                    TextView price = new TextView(view.getContext());
                    price.setText(orderItem.getPrice()+"");
                    price.setTextSize(18);

                    // check if the customer is club member for 10% discount
                    if(isClubMember) {
                        totalPayment += orderItem.getPrice()*0.9;
                    }
                    else{
                        totalPayment += orderItem.getPrice();
                    }
                    tr.addView(item_name, tableRowItemNameParams);
                    tr.addView(price, tableRowPriceParams);
                    tableLayout.addView(tr);
                }

                //show the total payment
                total_payment_tv = (TextView)view.findViewById(R.id.total_payment);
                total_payment_tv.setText("סה\"כ לתשלום: " + new DecimalFormat("##.##").format(totalPayment));
            }
            return tableLayout;
        }

    }

}