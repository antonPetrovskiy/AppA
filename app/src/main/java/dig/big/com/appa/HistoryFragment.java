package dig.big.com.appa;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Collections;
import java.util.List;

import dig.big.com.appa.sql.DatabaseHandler;
import dig.big.com.appa.sql.Link;

public class HistoryFragment extends Fragment {
    static View rootView;
    static LinksAdapter adapter;
    static ListView listView;
    static Context context;
    static List<Link> list;
    static List<Link> listNotSorted;

    public HistoryFragment() {
    }

    public static HistoryFragment newInstance(Context c) {
        HistoryFragment fragment = new HistoryFragment();
        context = c;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_history, container, false);

        init();
        listeners();
        updateList();

        return rootView;
    }

    public void init(){
        list = MainActivity.db.getAllLinks();
        listNotSorted = MainActivity.db.getAllLinks();
        listView = rootView.findViewById(R.id.list);
    }

    public void listeners(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                list = MainActivity.db.getAllLinks();
                listNotSorted = MainActivity.db.getAllLinks();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setComponent(new ComponentName("dig.big.com.appb","dig.big.com.appb.MainActivity"));
                intent.putExtra("fromA","history");
                intent.putExtra("link",listNotSorted.get(i).get_link());
                intent.putExtra("status",listNotSorted.get(i).get_status());
                intent.putExtra("time",listNotSorted.get(i).get_time());
                startActivity(intent);
            }
        });
    }

    public void updateList(){
        List<Link> list = MainActivity.db.getAllLinks();
        adapter = new LinksAdapter(context, list);
        listView.setAdapter(adapter);
    }

    public void sortByStatus(){
        List<Link> list = MainActivity.db.getAllLinks();
        Collections.sort(list, Link.COMPARE_BY_STATUS);
        adapter = new LinksAdapter(context, list);
        listView.setAdapter(adapter);

    }
}

