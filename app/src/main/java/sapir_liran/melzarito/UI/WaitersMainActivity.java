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

public class WaitersMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth auth;
    private TablesFragment tablesFragment;
    private OpenOrdersFragment openOrdersFragment;
    private ClubMembersFragment clubMembersFragment;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiters_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        auth = FirebaseAuth.getInstance();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public void onBackPressed() {

        int count = fragmentManager.getBackStackEntryCount();

        if (count == 0) {
            fragmentManager.popBackStack();
        } else {
            fragmentManager.popBackStack();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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

        if (id == R.id.tables) {
          if(tablesFragment ==null){
              tablesFragment=new TablesFragment();
          }
            setTitle(R.string.title_fragment_tables);
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, tablesFragment )
                    .commit();

        } else if (id == R.id.open_orders) {
            if(openOrdersFragment ==null){
                openOrdersFragment=new OpenOrdersFragment();
            }
            setTitle(R.string.title_fragment_openorders);

            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, openOrdersFragment )
                    .commit();

        } else if (id == R.id.club) {
            if(clubMembersFragment ==null){
                clubMembersFragment=new ClubMembersFragment();
            }
            setTitle(R.string.title_fragment_club);
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, clubMembersFragment )
                    .commit();

        } else if (id == R.id.logout_menu) {
            auth.signOut();
            startActivity(new Intent(WaitersMainActivity.this, LoginActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
