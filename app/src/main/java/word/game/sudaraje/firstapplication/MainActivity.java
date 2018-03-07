package word.game.sudaraje.firstapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OneFragment.OnFragmentInteractionListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Session session = new Session(getApplicationContext());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("Play");
        tabLayout.getTabAt(1).setText("Hint");
    }

    private void setupViewPager(ViewPager viewPager) {

        adapter.addFragment(new OneFragment(), "ONE");
        adapter.addFragment(new TwoFragment(), "TWO");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.main, menu);
        Session session = new Session(getApplicationContext());
        menu.add(0, 1, 1, menuIconWithText(getResources().getDrawable(R.mipmap.ic_cached_black_24dp), "Refresh"));
        menu.add(0, 2, 2, menuIconWithText(getResources().getDrawable(R.mipmap.ic_sort_black_24dp), "Level"));
        menu.add(0, 3, 3, menuIconWithText(getResources().getDrawable(R.mipmap.ic_power_settings_new_black_24dp), session.getUsename()));
        return true;
    }
    private CharSequence menuIconWithText(Drawable r, String title) {

        r.setBounds(0, 0, r.getIntrinsicWidth(), r.getIntrinsicHeight());
        SpannableString sb = new SpannableString("    " + title);
        ImageSpan imageSpan = new ImageSpan(r, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sb;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 3:
                Session session = new Session(getApplicationContext());
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                AccessToken.setCurrentAccessToken(null);
                Bundle parameters = new Bundle();
                parameters.putString("isFbLoggedIn", session.isFbLoggedIn());
                parameters.putString("isGooglePlusLoggedIn", session.isGoogleLoggedIn());
                intent.putExtras(parameters);
                startActivity(intent);
                session.deleteToken();
                return true;
            case 1:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure...!!!")
                        .setTitle("Refresh");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //refresh both the fragments on ok click
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
//            case R.id.help_menu:
//                Toast.makeText(this, "You have selected Help Menu", Toast.LENGTH_SHORT).show();
//                return true;
            case 2:
                final CharSequence[] items = {" Easy "," Medium "," Hard "};

                // Creating and Building the Dialog
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Select The Difficulty Level");
                Dialog levelDialog = new Dialog(this);
                alert.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        String wordLength = new String();
                        switch(item)
                        {
                            case 0:
                                wordLength = "3";
                                break;
                            case 1:
                                wordLength = "4";
                                break;
                            case 2:
                                wordLength = "5";
                                break;
                        }
                        dialog.dismiss();

                        Bundle bundle = new Bundle();
                        bundle.putString("wordLength", wordLength);
                        OneFragment oneFragment = new OneFragment();
                        oneFragment.setArguments(bundle);
                        adapter.setBundle(bundle);
                        adapter.notifyDataSetChanged();
                    }
                });
                levelDialog = alert.create();
                levelDialog.show();

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

    public interface FragmentRefreshListener{
        void onRefresh();
    }

    @Override
    public void onFragmentInteraction(String stringEntered) {

        TwoFragment secondFragment = new TwoFragment();
        secondFragment.valueChange(stringEntered);

    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter{
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private Bundle fragmentBundle = new Bundle();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        public void setBundle(Bundle data){
            fragmentBundle = data;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    OneFragment oneFragment = new OneFragment();
                    oneFragment.setArguments(fragmentBundle);
                    return oneFragment;
                case 1:
                    return new TwoFragment();
                default:
                    return mFragmentList.get(position);
            }

        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
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