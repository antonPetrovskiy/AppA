package dig.big.com.appa;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dig.big.com.appa.sql.Link;

public class LinksAdapter  extends ArrayAdapter<Link> {
    public static List<Link> linkList;
    private Context context;
    private LayoutInflater mInflater;

    public LinksAdapter(Context context, List<Link> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        linkList = objects;
    }

    @Override
    public Link getItem(int position) {
        return linkList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final LinksAdapter.ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.layout_link_view, parent, false);
            vh = LinksAdapter.ViewHolder.create((LinearLayout) view);
            view.setTag(vh);
        } else {
            vh = (LinksAdapter.ViewHolder) convertView.getTag();
        }

        Link item = getItem(position);
        vh.textViewLink.setText(item.get_link());
        vh.textViewStatus.setText(item.get_status());
        vh.textViewTime.setText(item.get_time());


        Date df = new java.util.Date(Long.valueOf(item.get_time()));
        vh.textViewTime.setText(new SimpleDateFormat("HH:mm").format(df));



        if(item.get_status().equals("1")){
            vh.rootView.setBackgroundColor(Color.GREEN);
        }else if(item.get_status().equals("2")){
            vh.rootView.setBackgroundColor(Color.RED);
        }else{
            vh.rootView.setBackgroundColor(Color.GRAY);
        }


        return vh.rootView;
    }


    private static class ViewHolder {
        public final LinearLayout rootView;
        public final TextView textViewLink;
        public final TextView textViewStatus;
        public final TextView textViewTime;

        private ViewHolder(LinearLayout rootView,TextView textViewLink, TextView textViewStatus, TextView textViewTime) {
            this.rootView = rootView;
            this.textViewLink = textViewLink;
            this.textViewStatus = textViewStatus;
            this.textViewTime = textViewTime;
        }

        public static LinksAdapter.ViewHolder create(LinearLayout rootView) {
            TextView textViewLink = rootView.findViewById(R.id.textViewLink);
            TextView textViewStatus = rootView.findViewById(R.id.textViewSatus);
            TextView textViewTime = rootView.findViewById(R.id.textViewTime);
            return new LinksAdapter.ViewHolder(rootView, textViewLink, textViewStatus, textViewTime);
        }



    }
}
