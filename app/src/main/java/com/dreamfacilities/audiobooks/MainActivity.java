package com.dreamfacilities.audiobooks;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.dreamfacilities.audiobooks.fragments.FragmentDetail;
import com.dreamfacilities.audiobooks.fragments.SelectorFragment;
import com.dreamfacilities.audiobooks.fragments.PreferencesFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MainPresenter.View {

    private BooksFilterAdapter adapter;
    private AppBarLayout appBarLayout;
    private TabLayout tabs;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private FloatingActionButton fab;
    private Toolbar toolbar;

    //private MainController controller;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int idContainer = (findViewById(R.id.small_container) != null) ? R.id.small_container : R.id.left_container;

        SelectorFragment firstFragment = new SelectorFragment();
        getFragmentManager().beginTransaction().add(idContainer, firstFragment).commit();

        adapter = BooksSingleton.getInstance(this.getApplicationContext()).getAdapter();

        presenter = new MainPresenter(new BooksRespository(BookSharedPrefenceStorage.getInstance(this)), this);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.clickFavoriteButton();
            }
        });

        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        //Tabs
        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("All"));
        tabs.addTab(tabs.newTab().setText("Releases"));
        tabs.addTab(tabs.newTab().setText("Readed"));
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0: //Todos
                        adapter.setRelease(false);
                        adapter.setReaded(false);
                        break;
                    case 1: //Nuevos
                        adapter.setRelease(true);
                        adapter.setReaded(false);
                        break;
                    case 2: //Leidos
                        adapter.setRelease(false);
                        adapter.setReaded(true);
                        break;

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        // Navigation Drawer
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //controller = new MainController(BookSharedPrefenceStorage.getInstance(this));

        // Username
        SharedPreferences pref = getSharedPreferences("com.dreamfacilities.audiobooks_internal", MODE_PRIVATE);
        String name = pref.getString("name", null);
        View headerLayout = navigationView.getHeaderView(0);
        TextView txtName = (TextView) headerLayout.findViewById(R.id.txtName);
        txtName.setText(String.format(getString(R.string.welcome_message), name));
        // User picture
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Uri imageUrl = user.getPhotoUrl();
        if (imageUrl != null) {
            NetworkImageView userPicture = (NetworkImageView) headerLayout.findViewById(R.id.imageView);
            App app = (App) getApplicationContext();
            userPicture.setImageUrl(imageUrl.toString(), VolleySingleton.getInstance(this).getImageLoader());
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_all) {
            adapter.setGenre("");
            adapter.notifyDataSetChanged();
        } else if (id == R.id.nav_epic) {
            adapter.setGenre(Book.G_EPICO);
            adapter.notifyDataSetChanged();
        } else if (id == R.id.nav_XIX) {
            adapter.setGenre(Book.G_S_XIX);
            adapter.notifyDataSetChanged();
        } else if (id == R.id.nav_thriller) {
            adapter.setGenre(Book.G_SUSPENSE);
            adapter.notifyDataSetChanged();
        } else if (id == R.id.nav_signout) {
            AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            SharedPreferences pref = getSharedPreferences(
                                    "com.dreamfacilities.audiobooks_internal", MODE_PRIVATE);
                            pref.edit().remove("provider").commit();
                            pref.edit().remove("email").commit();
                            pref.edit().remove("name").commit();
                            Intent i = new Intent(MainActivity.this, CustomLoginActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                    | Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                            finish();
                        }
                    });
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(
                R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(
                R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.menu_preferences) {

            openPreferences();
            Toast.makeText(this, "Preferences", Toast.LENGTH_LONG).show();

            return true;
        } else if (id == R.id.menu_about) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("About");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.create().show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openPreferences() {
        int idContainer = (findViewById(R.id.small_container) != null) ?
                R.id.small_container : R.id.left_container;
        PreferencesFragment prefFragment = new PreferencesFragment();
        getFragmentManager()
                .beginTransaction()
                .replace(idContainer, prefFragment)
                .addToBackStack(null)
                .commit();
    }

    protected void onNewIntent(Intent intent) {
        if (intent.getAction() != null) {
            if (intent.getAction().equals(Intent.ACTION_SEARCH)) {
                adapter.setSearch(intent.getStringExtra(SearchManager.QUERY));
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void showDetail(String key) {
        presenter.openDetail(key);
    }

    @Override
    public void showNotLastView() {
        Toast.makeText(this, "Sin última vista", Toast.LENGTH_LONG).show();
    }

    public void goToLastListen() {
        presenter.clickFavoriteButton();
    }

    public void showElements(boolean show) {
        appBarLayout.setExpanded(show);
        toggle.setDrawerIndicatorEnabled(show);

        if (show) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            tabs.setVisibility(View.VISIBLE);
        } else {
            tabs.setVisibility(View.GONE);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    public void showFragmentDetail(String key) {
        FragmentDetail fragmentDetail = (FragmentDetail) getFragmentManager()
                .findFragmentById(R.id.fragment_detail);

        if (fragmentDetail != null) {

            fragmentDetail.setInfoBook(key);

        } else {

            FragmentDetail newFragmentDetail = new FragmentDetail();
            Bundle args = new Bundle();
            args.putString(FragmentDetail.ARG_ID_BOOK, key);
            newFragmentDetail.setArguments(args);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.small_container, newFragmentDetail);
            // This add the transaction the the navigation pool, so when the user click back
            // the transaction will be removed instead of destroy the Activity
            transaction.addToBackStack(null);
            transaction.commit();

        }
    }
}
