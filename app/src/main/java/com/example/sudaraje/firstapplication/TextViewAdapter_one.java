package com.example.sudaraje.firstapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by sudaraje on 6/18/2017.
 */
public class TextViewAdapter_one extends BaseAdapter {


    private TextView sno,words,bulls,cows;
    private static String bullText, cowText, word, snoText;
    private static Context context;
    private View lv;
    private static int count ;
    private static JSONObject jo;
    private static JSONArray ja;

    public TextViewAdapter_one(Context context) {
        this.context = context;
    }

    public void getValuesFromTextBox(ArrayList<String> al) throws JSONException {
        System.out.println(al.toString());
        if(al.size() != 0) {
            count++;

            jo = new JSONObject();
            jo.put("sno",count+"");
            jo.put("words",al.get(0));
            jo.put("bulls",al.get(1));
            jo.put("cows",al.get(2));

            ja = new JSONArray();
            ja.put(jo);

            word = al.get(0);
            bullText = al.get(1) + "";
            cowText = al.get(2) + "";
            notifyDataSetChanged();
        }
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
        return count;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

//        View v = convertView;
//        MyViewHolder viewHolder;

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_view, parent, false);
//            v = inflater.inflate(R.layout.list_view, null);
//            viewHolder = new MyViewHolder(v);
//            v.setTag(viewHolder);

            sno = (TextView) convertView.findViewById(R.id.SNO);
            words = (TextView) convertView.findViewById(R.id.words);
            bulls = (TextView) convertView.findViewById(R.id.bulls);
            cows = (TextView) convertView.findViewById(R.id.cows);

//            convertView.setTag(R.id.SNO, sno);
//            convertView.setTag(R.id.words, words);
//            convertView.setTag(R.id.bulls, bulls);
//            convertView.setTag(R.id.cows, cows);
        }
//        else {
//            sno = (TextView) convertView.getTag(R.id.SNO);
//            words = (TextView) convertView.getTag(R.id.words);
//            bulls = (TextView) convertView.getTag(R.id.bulls);
//            cows = (TextView) convertView.getTag(R.id.cows);
//        }

//        else {
//            viewHolder = (MyViewHolder) v.getTag();
//        }
        //System.out.println("------inside getview------------"+sno+"\n"+word+"\n"+bullText+"\n"+cowText);
//        viewHolder.sno.setText(count+"");
//        viewHolder.words.setText(word);
//        viewHolder.bulls.setText(bullText);
//        viewHolder.cows.setText(cowText);

//        try {
//            JSONObject joey = new JSONObject();
//
//            if(position == 0){
//                joey = ja.getJSONObject(position);
//            }
//            else{
//                joey = ja.getJSONObject(position-1);
//            }
//            System.out.println(joey.toString());
//            sno.setText(joey.get("sno")+"");
//            words.setText(joey.get("words")+"");
//            bulls.setText(joey.get("bulls")+"");
//            cows.setText(joey.get("cows")+"");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        sno.setText(count+"");
        words.setText(word);
        bulls.setText(bullText);
        cows.setText(cowText);

//        return v;
        return convertView;
    }

    class MyViewHolder {
        public TextView sno,words,bulls,cows;
        public MyViewHolder(View base) {
            sno = (TextView) base.findViewById(R.id.SNO);
            words = (TextView) base.findViewById(R.id.words);
            bulls = (TextView) base.findViewById(R.id.bulls);
            cows = (TextView) base.findViewById(R.id.cows);
        }
    } }
