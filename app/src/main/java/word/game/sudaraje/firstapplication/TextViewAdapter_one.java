package word.game.sudaraje.firstapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by sudaraje on 6/18/2017.
 */
public class TextViewAdapter_one extends BaseAdapter {


    private TextView sno,words,bulls,cows;
    private static Context context;
    private View lv;
    public List<HashMap<String,String>> values;

    public TextViewAdapter_one(Context context, List<HashMap<String,String>> values) {
        this.context = context;
        this.values=values;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_view, parent, false);

        sno = (TextView) convertView.findViewById(R.id.SNO);
        words = (TextView) convertView.findViewById(R.id.words);
        bulls = (TextView) convertView.findViewById(R.id.bulls);
        cows = (TextView) convertView.findViewById(R.id.cows);

        sno.setText(position+1+"");
        words.setText(values.get(position).get("entered_string"));
        bulls.setText(values.get(position).get("bulls"));
        cows.setText(values.get(position).get("cows"));

        return convertView;
    }
}
