package com.example.sudaraje.firstapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


public class OneFragment extends Fragment{

    private Button goButton = null;
    private EditText editText = null;
    private CharSequence prevSeq = null;
    private boolean check = false;
    private char c;
    private String[] str;
    private String ltrToFind = null;
    private int bulls,cows;
    private CustomAdapter myAdapter;
    private OnFragmentInteractionListener mListener;

    public OneFragment() {
        str = new String[]{"hike","mike","mole","mile","lite","peak","pear","mine","shut","nose","bike","mild","lick","neat","vein","kith","nerd"};
        bulls=0;
        cows=0;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);
        //randomly select a value from the string array
        Random rn = new Random();
        int randomNo = rn.nextInt(10) + 1;
        ltrToFind = str[randomNo];
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity a = null;

        if (context instanceof Activity){
            a=(Activity) context;
        }
        try {
            mListener = (OnFragmentInteractionListener) a;
        } catch (ClassCastException e) {
            throw new ClassCastException(a.toString() + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_one, container, false);
        ListView listView = (ListView)view.findViewById(R.id.customlist);
        listView.setItemsCanFocus(true);

        myAdapter= new CustomAdapter();
        listView.setAdapter(myAdapter);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            listView.setNestedScrollingEnabled(true);
        }

        return view;

    }

    public class CustomAdapter extends BaseAdapter {

        public ArrayList<String> myItems = new ArrayList();

        public CustomAdapter() {
            myItems.add("");
            notifyDataSetChanged();
        }

        public int getCount() {
            return myItems.size();
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                convertView = inflater.inflate(R.layout.list_view, parent, false);

                //get the go button id and disable it
                goButton = (Button) convertView.findViewById(R.id.go_button);
                goButton.setVisibility(View.GONE);

                final EditText editText = (EditText) convertView.findViewById(R.id.editText);

                editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    public void onFocusChange(View v, boolean hasFocus) {
                    }
                });

                editText.addTextChangedListener(new TextWatcher() {

                    public void afterTextChanged(Editable s) {
                    }

                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {
                    }

                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {

                        goButton.setVisibility(View.GONE);
                        check = false;

                        for(int i=0 ; i<s.length() ; i++)
                            for(int j=0 ; j<s.length() ; j++)
                                if(s.charAt(i) == s.charAt(j) && i!=j) {
                                    editText.setError("Duplicate");
                                    check = true;
                                    break;
                                }
                        if(s.length() == 4 && !check) {
                            goButton.setVisibility(View.VISIBLE);
                        }
                    }
                });

                //get the id of bulls and cows
                final TextView bullText = (TextView) convertView.findViewById(R.id.bulls);
                final TextView cowText = (TextView) convertView.findViewById(R.id.cows);

                goButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        bulls = cows = 0;
                        // Perform action on click
                        String strEntered = editText.getText().toString();
                        System.out.println(strEntered);
                        System.out.println(ltrToFind);
                        //different position increment the value of cows
                        for (int i = 0; i < ltrToFind.length(); i++)
                            for (int j = 0; j < strEntered.length(); j++)
                                if (ltrToFind.charAt(i) == strEntered.charAt(j) && i != j)
                                    cows++;
                        //same position increment the value of bulls
                        for (int i = 0; i < ltrToFind.length(); i++)
                            if (ltrToFind.charAt(i) == strEntered.charAt(i))
                                bulls++;

                        //System.out.println("bull and cow values ::: " + bulls + "\n" + cows);
                        //pass the values to next fragment if both bull and cow are zero
                        if(bulls == 0 && cows == 0)
                            mListener.onFragmentInteraction(strEntered);

                        bullText.setText(bulls + "");
                        cowText.setText(cows + "");

                        goButton.setEnabled(false);
                        editText.setEnabled(false);
                        bullText.setEnabled(false);
                        cowText.setEnabled(false);

                        if(bulls != 4) {
                            myItems.add("");
                            notifyDataSetChanged();
                        }
                        else { //once the word is found
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("You Found it")
                                    .setTitle("Congrats");
                            AlertDialog dialog = builder.create();
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                            builder.show();
                        }
                    }
                });

            }
            return convertView;
        }

        public long getItemId(int position) {
            return position;
        }
        public Object getItem(int position) {
            return position;
        }
    }


    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String stringEntered);
    }



}
