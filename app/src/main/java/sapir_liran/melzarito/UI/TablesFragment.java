package sapir_liran.melzarito.UI;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import Logic.RestaurantManager;
import Logic.Table;
import sapir_liran.melzarito.R;

import static sapir_liran.melzarito.R.drawable.table;
import static sapir_liran.melzarito.R.drawable.table_button;

public class TablesFragment extends android.app.Fragment {
    View view;
        GridView gridView;
    FragmentManager fragmentManager;
    TableOrderFragment tableOrderFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       view= inflater.inflate(R.layout.tables_fragment, container, false);
        ArrayList<Button> btn = new ArrayList<>();
        tableOrderFragment = new TableOrderFragment();
        gridView = (GridView) view.findViewById(R.id.tables_layout);
        fragmentManager = getFragmentManager();
        gridView.setLayoutParams(new GridView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));


        TableAdapter tablesAdapter = new TableAdapter(getActivity(), LoginActivity.restaurantManager.getTables().size());
        gridView.setAdapter(tablesAdapter);


        return view;

    }

    
    class TableAdapter extends BaseAdapter {

        private final Context mContext;
        private int size;

        // 1
        public TableAdapter(Context context, int size) {
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
            Button dummyButton = new Button(mContext);
            dummyButton.setText(String.valueOf(position));

            dummyButton.setGravity(Gravity.CENTER);
            dummyButton.setId(position + 1);
            dummyButton.setBackgroundResource(R.drawable.buttonshape);
            Drawable img = mContext.getResources().getDrawable(R.drawable.table_image, null);
            dummyButton.setCompoundDrawablesWithIntrinsicBounds( null, null, null, img);
            dummyButton.setTextSize(30);
            dummyButton.setText(String.valueOf(position + 1));
            final int tableNumber = position+1;
            dummyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, tableOrderFragment ).addToBackStack(TablesFragment.class.getName())
                            .commit();
                    tableOrderFragment.setTableNumber(tableNumber);

                }
            });

            return dummyButton;
        }

    }
}



