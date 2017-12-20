package com.example.sudaraje.firstapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TextViewAdapter extends BaseAdapter {
    private Context context;
    private static char[] textViewValues = new char[]{'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    private static ArrayList<Character> al=new ArrayList<Character>();
    private static String textToDelete ;
    private static ArrayList<Character> alc = new ArrayList<Character>();

    public TextViewAdapter(Context context,ArrayList<Character>alc) {
        this.context = context;
        this.alc = alc;
    }

    public void changeValues(ArrayList<Character> alc){
        this.alc = alc;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;
        gridView = inflater.inflate(R.layout.item, null);
        TextView textView = (TextView) gridView.findViewById(R.id.grid_item_label);
        textView.setText(alc.get(position).toString().toUpperCase());
        return gridView;
    }

    @Override
    public int getCount() {
        return alc.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}