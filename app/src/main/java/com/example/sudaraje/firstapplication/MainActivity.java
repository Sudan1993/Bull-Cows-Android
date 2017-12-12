package com.example.sudaraje.firstapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.HeaderViewListAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OneFragment.OnFragmentInteractionListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("Play");
        //tabLayout.removeTabAt(1);
        tabLayout.getTabAt(1).setText("Hint");
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "ONE");
        adapter.addFragment(new TwoFragment(), "TWO");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh_menu:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure...!!!")
                        .setTitle("Refresh");
                AlertDialog dialog = builder.create();
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //refresh both the fragments on ok click
                        OneFragment oneFragment = new OneFragment();
                        TwoFragment twoFragment = new TwoFragment();
                        //refreshFragments(oneFragment,"refresh");
                        //refreshFragments(twoFragment,"");
                        getFragmentRefreshListener().onRefresh();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
                return true;
            case R.id.help_menu:
                Toast.makeText(this, "You have selected Help Menu", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public FragmentRefreshListener getFragmentRefreshListener() {
        return fragmentRefreshListener;
    }

    public void setFragmentRefreshListener(FragmentRefreshListener fragmentRefreshListener) {
        this.fragmentRefreshListener = fragmentRefreshListener;
    }

    private FragmentRefreshListener fragmentRefreshListener;

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//        Button b = (Button)findViewById(R.id.btnRefreshFragment);
//
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(getFragmentRefreshListener()!=null){
//                    getFragmentRefreshListener().onRefresh();
//                }
//            }
//        });
//
//
//    }


    public interface FragmentRefreshListener{
        void onRefresh();
    }

    public void refreshFragments(Fragment fragmentRef,String bundleString)
    {
        Bundle bundle = new Bundle();
        if( !bundleString.isEmpty() ) {
            bundle.putString("txtToFragment", bundleString);
            fragmentRef.setArguments(bundle);
        }
        android.app.FragmentManager fm = getFragmentManager();
        android.app.FragmentTransaction ft = fm.beginTransaction();

        getSupportFragmentManager()
                .beginTransaction()
                .detach(fragmentRef)
                .attach(fragmentRef)
                .commit();
        ft.commit();
    }

    @Override
    public void onFragmentInteraction(String stringEntered) {

        TwoFragment secondFragment = new TwoFragment();
        secondFragment.valueChange(stringEntered);
        //refreshFragments(secondFragment,"");
        try {
//            android.app.FragmentManager fm = getFragmentManager();
//            android.app.FragmentTransaction ft = fm.beginTransaction();
//            Bundle bundle = new Bundle();
//            bundle.putString("txtToBeDeleted", stringEntered);
//            secondFragment.setArguments(bundle);
//            refreshFragments(secondFragment,stringEntered);
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .detach(secondFragment)
//                    .attach(secondFragment)
//                    .commit();
//            ft.commit();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}