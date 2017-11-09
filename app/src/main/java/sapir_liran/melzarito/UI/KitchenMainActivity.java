package sapir_liran.melzarito.UI;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import sapir_liran.melzarito.R;

public class KitchenMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth auth;
    private KitchenOpenOrdersFragment openOrdersFragment;
    private UpdateStockFragment updateStockFragment;
    private AddSpecialFragment addSpecialFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragmentManager = getFragmentManager();
        //default screen
        if (openOrdersFragment == null) {
            openOrdersFragment = new KitchenOpenOrdersFragment();
            setTitle(R.string.title_fragment_openorders);
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame_kitchen, openOrdersFragment)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_kitchen_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    //return back fragment
    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            fragmentManager.popBackStack();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_kitchen_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.waiters_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        fragmentManager = getFragmentManager();

        //kitchen open orders screen
        if (id == R.id.kitchen_open_order) {
            if (openOrdersFragment == null) {
                openOrdersFragment = new KitchenOpenOrdersFragment();
            }
            setTitle(R.string.title_fragment_openorders);
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame_kitchen, openOrdersFragment)
                    .commit();
        }
        //update stock screen
        else if (id == R.id.update_stock) {
            if (updateStockFragment == null) {
                updateStockFragment = new UpdateStockFragment();
            }
            setTitle(R.string.title_update_stocks);
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame_kitchen, updateStockFragment)
                    .commit();
        }
        //update specials screen
        else if (id == R.id.update_specials) {
            if (addSpecialFragment == null) {
                addSpecialFragment = new AddSpecialFragment();
            }
            setTitle(R.string.title_fragment_add_new_special);
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame_kitchen, addSpecialFragment)
                    .commit();
        }
        //choose role screen
        else if (id == R.id.choose_employee_role) {
            finish();

        }
        //logout
        else if (id == R.id.logout_menu) {
            if(auth!=null)
            auth.signOut();
            startActivity(new Intent(KitchenMainActivity.this, LoginActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_kitchen_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
