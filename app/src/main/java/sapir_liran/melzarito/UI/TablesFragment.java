package sapir_liran.melzarito.UI;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import Logic.RestaurantManager;
import sapir_liran.melzarito.R;

import static android.R.string.no;

public class TablesFragment extends android.app.Fragment {
    private View view;
    private GridView gridView;
    private FragmentManager fragmentManager;
    private TableOrderFragment tableOrderFragment;
    private Button refresh_btn;
    private int tableNum;
    private RestaurantManager restaurantManager = RestaurantManager.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.tables_fragment, container, false);
        getActivity().setTitle(R.string.title_fragment_tables);
        tableOrderFragment = new TableOrderFragment();
        gridView = (GridView) view.findViewById(R.id.tables_layout);
        fragmentManager = getFragmentManager();

        tableNum = 0;

        refresh_btn = (Button) view.findViewById(R.id.refresh_button);
        //no data was loaded
        if(restaurantManager.getTables() == null){
            refresh_btn.setText(R.string.data_error_text);
            refresh_btn.setTextColor(Color.RED);
        }
        else {
            refresh_btn.setText(R.string.refresh_btn_text);
            refresh_btn.setTextColor(Color.BLACK);
        }


            refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (restaurantManager.getTables() != null)
                    tableNum = restaurantManager.getTables().size();
                TableAdapter tablesAdapter = new TableAdapter(getActivity(), tableNum);
                gridView.setAdapter(tablesAdapter);

            }
        });

        //load tables from DB
        refresh_btn.callOnClick();

        return view;
    }

    class TableAdapter extends BaseAdapter {

        private final Context mContext;
        private int size;

        public TableAdapter(Context context, int size) {
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
            Button dummyButton = new Button(mContext);
            dummyButton.setText(String.valueOf(position));
            dummyButton.setGravity(Gravity.CENTER);
            dummyButton.setId(position + 1);
            dummyButton.setBackgroundResource(R.drawable.buttonshape);
            Drawable img = mContext.getResources().getDrawable(R.drawable.table_image, null);
            dummyButton.setCompoundDrawablesWithIntrinsicBounds(null, null, null, img);
            dummyButton.setTextSize(30);
            dummyButton.setText(String.valueOf(position + 1));
            final int tableNumber = position + 1;
            dummyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, tableOrderFragment).addToBackStack(TablesFragment.class.getName())
                            .commit();
                    tableOrderFragment.setTableNumber(tableNumber);

                }
            });

            return dummyButton;
        }

    }
}
