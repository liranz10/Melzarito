package sapir_liran.melzarito.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sapir_liran.melzarito.R;

public class TablesFragment extends android.app.Fragment {
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       view= inflater.inflate(R.layout.tables_fragment, container, false);
        return view;

    }
}