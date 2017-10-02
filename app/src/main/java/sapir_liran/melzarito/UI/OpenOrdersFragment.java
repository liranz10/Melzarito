package sapir_liran.melzarito.UI;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sapir_liran.melzarito.R;

public class OpenOrdersFragment extends Fragment {

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.open_orders_fragment, container, false);
        return view;

    }
}
