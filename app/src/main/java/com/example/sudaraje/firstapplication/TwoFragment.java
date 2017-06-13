package com.example.sudaraje.firstapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class TwoFragment extends android.support.v4.app.Fragment {
    public String[] textViewValues;
    private String txtToBeDeleted = null;

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
        Bundle bundle = this.getArguments();
        if (bundle != null)
            txtToBeDeleted = getArguments().getString("txtToBeDeleted");
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        GridView gridview = (GridView) view.findViewById(R.id.gridviewCustom);
        gridview.setAdapter(new TextViewAdapter(this.getContext(), textViewValues,txtToBeDeleted));

        return view;

    }

}