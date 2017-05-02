package com.example.sudaraje.firstapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;


public class TwoFragment extends Fragment{
    public String[] textViewValues;
    private String txtToBeDeleted;

    public TwoFragment() {
        // Required empty public constructor
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
        //return inflater.inflate(R.layout.fragment_two, container, false);
        Bundle bundle = this.getArguments();
        if(bundle!=null)
            txtToBeDeleted = getArguments().getString("txtToBeDeleted");
        System.out.println(txtToBeDeleted + "strTex :::::::::::::::::::");
        View view=inflater.inflate(R.layout.fragment_two, container, false);
        TextView textView = (TextView) view.findViewById(R.id.textViewCustom);
        textView.setText("");
        GridView gridview = (GridView) view.findViewById(R.id.gridviewCustom);
        gridview.setAdapter(new TextViewAdapter(this.getContext(),textViewValues));
        gridview.setClickable(false);
        gridview.setFocusable(false);
        gridview.setFocusableInTouchMode(false);

        return view;

    }

    public void removeLetters(String strEntered){
        System.out.println("String from fragment one :::: " + strEntered);
        //ViewGroup vg = getActivity().findViewById (R.id.gridviewCustom);
    }

}