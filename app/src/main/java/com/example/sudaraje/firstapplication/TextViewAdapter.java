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

    public TextViewAdapter(Context context,String ch[],String txtToBeDeleted) {
        this.context = context;
        //ch = new char[]{'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        //textViewValues = ch;
        if(al.size()==0) {
            for(int i = 0; i < 26; i++) {
                al.add(textViewValues[i]);
            }
        }
        this.alc = alc;
        if(txtToBeDeleted != null) {
            textToDelete = txtToBeDeleted.toUpperCase();
        }
    }

    public void chagneValues(ArrayList<Character> alc){
        this.alc = alc;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
//
//        if (convertView == null) {
        gridView = new View(context);
        gridView = inflater.inflate(R.layout.item, null);
            TextView textView = (TextView) gridView.findViewById(R.id.grid_item_label);
            textView.setText(al.get(position).toString().toUpperCase());
        //System.out.print("inside getview  " + alc.get(position).toString() + "\n");
            if(this.textToDelete != null) {
                String strPresent = textView.getText().toString().toUpperCase();
                for(char ch : strPresent.toCharArray()) {
                    for(int i=0 ; i<4 ;i++)
                        if(ch == textToDelete.charAt(i)) {
                            int index = al.indexOf(ch);
                            if(index != -1)
                                al.set(index,' ');
                            textView.setVisibility(View.GONE);
                        }
                }
            }
//        } else {
//            gridView = convertView;
//        }
        return gridView;
    }

    @Override
    public int getCount() {
        return al.size();
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