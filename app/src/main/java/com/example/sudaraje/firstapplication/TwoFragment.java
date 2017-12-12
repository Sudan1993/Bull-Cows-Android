package com.example.sudaraje.firstapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

public class TwoFragment extends android.support.v4.app.Fragment {
    public String[] textViewValues;
    private String txtToBeDeleted = null;
    public static ArrayList<Character> alc = null;
    public static TextViewAdapter myAdapter = null;

    public TwoFragment() {
        // Required empty public constructor
        String str = "abcdefghijklmnopqrstuvwxyz";
        alc = new ArrayList<Character>();
        for(int i=0 ;i<str.length() ; i++) {
            alc.add(str.charAt(i));
        }
    }

    public void valueChange(String txtToBeDeleted){
        txtToBeDeleted = txtToBeDeleted.toLowerCase();

        System.out.println("before" + alc+"");
        for(int i=0 ; i<alc.size() ; i++)
            for(int j=0 ; j<txtToBeDeleted.length() ; j++)
                if(alc.get(i) == txtToBeDeleted.charAt(j))
                    alc.set(i,' ');
        System.out.println("after" + alc+"");
        myAdapter.chagneValues(alc);
        myAdapter.notifyDataSetChanged();

        }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textViewValues  = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        Bundle bundle = this.getArguments();
        if (bundle != null)
            txtToBeDeleted = getArguments().getString("txtToBeDeleted");
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        GridView gridview = (GridView) view.findViewById(R.id.gridviewCustom);
        myAdapter = new TextViewAdapter(this.getContext(), textViewValues,txtToBeDeleted);
        gridview.setAdapter(myAdapter);

        return view;
    }


}