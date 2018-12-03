package dig.big.com.appa;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.WindowManager;
import android.widget.TextView;

import dig.big.com.appa.sql.DatabaseHandler;
import dig.big.com.appa.sql.Link;

public class MainActivity extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    static DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        init();
        actions();

    }

    public void init(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        db = new DatabaseHandler(this);
    }

    public void actions(){
        ActionReceiver receiver = new ActionReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("dig.big.com.appa.add");
        filter.addAction("dig.big.com.appa.update");
        filter.addAction("dig.big.com.appa.remove");
        registerReceiver(receiver, filter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_time) {
            HistoryFragment.newInstance(getApplicationContext()).updateList();
            return true;
        }

        if (id == R.id.action_status) {
            HistoryFragment.newInstance(getApplicationContext()).sortByStatus();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0)
                return TestFragment.newInstance();
            if(position == 1)
                return HistoryFragment.newInstance(getApplicationContext());
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    public class ActionReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("dig.big.com.appa.add".equalsIgnoreCase(intent.getAction())) {
                String link = intent.getStringExtra("link");
                String status = intent.getStringExtra("status");
                String time = intent.getStringExtra("time");
                db.addLink(new Link(link,status,time));
                HistoryFragment.newInstance(getApplicationContext()).updateList();

            }
            if ("dig.big.com.appa.remove".equalsIgnoreCase(intent.getAction())) {
                String link = intent.getStringExtra("link");
                String status = intent.getStringExtra("status");
                String time = intent.getStringExtra("time");
                db.deleteLink(db.findLink(link));
                HistoryFragment.newInstance(getApplicationContext()).updateList();

            }
            if ("dig.big.com.appa.update".equalsIgnoreCase(intent.getAction())) {
                String link = intent.getStringExtra("link");
                String status = intent.getStringExtra("status");
                String time = intent.getStringExtra("time");
                db.updateLink(db.findLink(link));
                HistoryFragment.newInstance(getApplicationContext()).updateList();

            }
        }
    }
}
