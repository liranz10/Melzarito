package sapir_liran.melzarito.UI;

import android.app.FragmentManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import Logic.Menu;
import Logic.MenuItem;
import Logic.RestaurantManager;
import sapir_liran.melzarito.R;

public class UpdateStockItemsFragment extends android.app.Fragment {
    View view;
    private int category;
    private String text;
    private TableLayout layout;
    private FragmentManager fragmentManager;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.out_of_stock_items_fragment, container, false);
        getActivity().setTitle(text);
         layout = (TableLayout) view.findViewById(R.id.out_of_stock_items_layout);
        if (layout.getChildCount()!=0)
            layout.removeAllViews();
        final Menu menu = LoginActivity.restaurantManager.getMenu();
        if(menu!=null){
        for(final MenuItem item : menu.getItems()) {
            if(item!=null){
            if (item.getCategory() == category) {

                TableRow tr = new TableRow(getActivity());
                tr.setBackgroundResource(R.drawable.buttonshape);
                tr.setGravity(Gravity.CENTER);
                TextView item_view = new TextView(view.getContext());
                item_view.setTextSize(24);
                item_view.setText(item.getName());
                Button out_of_stock_btn = new Button(view.getContext());
                out_of_stock_btn.setText("אזל מהמלאי");
                Drawable img = view.getContext().getDrawable(R.drawable.outofstock);
                out_of_stock_btn.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                out_of_stock_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoginActivity.restaurantManager.OutOfStock(item);

                    }
                });

                tr.addView(item_view);
                tr.addView(out_of_stock_btn);
                layout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            }

            }
        }
        }



        return view;

    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setText(String text) {
        this.text = text;
    }


}