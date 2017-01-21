package com.dreamfacilities.audiobooks;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.dreamfacilities.audiobooks.fragments.FragmentDetail;
import com.dreamfacilities.audiobooks.fragments.FragmentSelector;


public class MainActivity extends AppCompatActivity {
    //private RecyclerView recyclerView;
    //private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if ((findViewById(R.id.small_container) != null) &&
                (getFragmentManager().findFragmentById(R.id.small_container) == null)) {

            FragmentSelector firstFragment = new FragmentSelector();
            getFragmentManager().beginTransaction()
                                .add(R.id.small_container, firstFragment).commit();

        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
/*
        App app = (App) getApplication();
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        recyclerView.setAdapter(app.getAdaptador());

        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);


        //Listener for item in recycler view
        app.getAdaptador().setOnItemClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(MainActivity.this, "Element " + recyclerView.getChildAdapterPosition(v) + " was selected", Toast.LENGTH_SHORT).show();
            }
        });
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void showDetail(int id){
        FragmentDetail fragmentDetail = (FragmentDetail) getFragmentManager().findFragmentById(R.id.fragment_detail);

        if(fragmentDetail != null){
            fragmentDetail.setInfoBook(id);
        } else {
            FragmentDetail newFragmentDetail = new FragmentDetail();
            Bundle args = new Bundle();
            args.putInt(FragmentDetail.ARG_ID_BOOK, id);
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
