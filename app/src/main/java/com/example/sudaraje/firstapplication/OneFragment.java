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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class OneFragment extends Fragment{

    private Button go_button = null;
    private EditText typedWord = null;
    private int bulls,cows;
    private boolean check = false;
    private String[] str;
    private String ltrToFind = null;
    private TextViewAdapter_one myAdapter;
    private OnFragmentInteractionListener mListener;
    List<HashMap<String,String>> values=new ArrayList<>();

    public OneFragment() {
        str = new String[]{"hike","mike","mole","mile","lite","peak","pear","mine","shut","nose","bike","mild","lick","neat","vein","kith","nerd"};
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
        typedWord = (EditText) view.findViewById(R.id.typedWord);
        go_button = (Button) view.findViewById(R.id.go_button);

        //get the go button and disable it
        go_button.setVisibility(View.GONE);

        ListView listView = (ListView)view.findViewById(R.id.customlist);
        listView.setItemsCanFocus(true);

        myAdapter= new TextViewAdapter_one(this.getContext(),values);
        listView.setAdapter(myAdapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            listView.setNestedScrollingEnabled(true);
        }

        typedWord.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                go_button.setVisibility(View.GONE);
                check = false;

                for(int i=0 ; i<s.length() ; i++)
                    for(int j=0 ; j<s.length() ; j++)
                        if(s.charAt(i) == s.charAt(j) && i!=j) {
                            typedWord.setError("Duplicate");
                            check = true;
                            break;
                        }
                if(s.length() == 4 && !check) {
                    go_button.setVisibility(View.VISIBLE);
                }
            }
        });

        go_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bulls = cows = 0;
                // Perform action on click
                String strEntered = typedWord.getText().toString();
                //different position increment the value of cows
                for (int i = 0; i < ltrToFind.length(); i++)
                    for (int j = 0; j < strEntered.length(); j++)
                        if (ltrToFind.charAt(i) == strEntered.charAt(j) && i != j)
                            cows++;
                //same position increment the value of bulls
                for (int i = 0; i < ltrToFind.length(); i++)
                    if (ltrToFind.charAt(i) == strEntered.charAt(i))
                        bulls++;

                System.out.println("bull and cow values ::: " + bulls + "\n" + cows);
                //pass the values to next fragment if both bull and cow are zero
                if(bulls == 0 && cows == 0)
                    mListener.onFragmentInteraction(strEntered);

                go_button.setVisibility(View.GONE);
                typedWord.setText("");

                if(bulls != 4) {

                    HashMap<String,String> value_entry = new HashMap();
                    value_entry.put("entered_string",strEntered);
                    value_entry.put("bulls",bulls+"");
                    value_entry.put("cows",cows+"");

                    values.add(value_entry);
                    myAdapter.notifyDataSetChanged();

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

        return view;
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String stringEntered);
    }



}
