package com.inskade.stint.ui.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.inskade.stint.R;
import com.inskade.stint.database.AppDatabase;
import com.inskade.stint.database.model.Item;
import com.inskade.stint.database.model.ItemCollection;
import com.inskade.stint.misc.TypefaceCache;
import com.inskade.stint.ui.fragments.CollectFragment;
import com.inskade.stint.ui.fragments.DeliverFragment;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int FRAGMENT_CONTAINER_ID = R.id.fragment_container;

    private NavigationTabStrip navigationTabStrip;
    private FragmentManager fragmentManager;
    private CollectFragment collectFragment;
    private DeliverFragment deliverFragment;

    private Typeface bebasBold;

    private TextView singleCost;
    private TextView totalCost;
    private ImageView addItem;

    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        setListeners();
        setupNavStrip();
        initFragments();
        setTypefaces();
    }

    private void setListeners() {
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AddNewItemActivity.class));
            }
        });
    }

    private void setupNavStrip() {
        navigationTabStrip.setTitles(R.string.tab_item_1, R.string.tab_item_2);
        navigationTabStrip.setOnTabStripSelectedIndexListener(new NavigationTabStrip.OnTabStripSelectedIndexListener() {
            @Override
            public void onStartTabSelected(String title, int index) {

            }

            @Override
            public void onEndTabSelected(String title, int index) {
                if(title.equals(getString(R.string.tab_item_1))) {
                    fragmentManager.beginTransaction().replace(FRAGMENT_CONTAINER_ID, collectFragment).commit();
                } else if(title.equals(getString(R.string.tab_item_2))) {
                    fragmentManager.beginTransaction().replace(FRAGMENT_CONTAINER_ID, deliverFragment).commit();
                }
            }
        });
    }

    private void initFragments() {
        //Fetch fragment manager
        fragmentManager = getSupportFragmentManager();

        //Initialize fragments
        collectFragment = new CollectFragment();
        deliverFragment = new DeliverFragment();

        loadLastOpenedFragment();
    }

    private void loadLastOpenedFragment() {
        //TODO: Load the last opened fragment instead of default fragment or provide an option for toggling the same in settings
        fragmentManager.beginTransaction().replace(FRAGMENT_CONTAINER_ID, collectFragment).commit();
        navigationTabStrip.setTabIndex(0, true);
    }

    private void setTypefaces() {
        //Load Typefaces
        bebasBold = TypefaceCache.get(getAssets(), "BebasNeueBold");

        singleCost.setTypeface(bebasBold);
        totalCost.setTypeface(bebasBold);
    }

    private void findViews() {
        singleCost = (TextView) findViewById(R.id.single_cost_textview);
        totalCost = (TextView) findViewById(R.id.total_cost_textview);
        navigationTabStrip = (NavigationTabStrip) findViewById(R.id.navigation_tab_strip);
        addItem = (ImageView) findViewById(R.id.add_new_list_icon);
    }
}
