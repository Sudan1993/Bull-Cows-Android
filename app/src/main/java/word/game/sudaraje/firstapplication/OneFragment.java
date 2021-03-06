package word.game.sudaraje.firstapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class OneFragment extends Fragment{

    private ImageButton go_button = null;
    private EditText typedWord = null;
    private int bulls,cows;
    private boolean check = false;
    private String[] str;
    private String ltrToFind = null;
    private TextViewAdapter_one myAdapter;
    private OnFragmentInteractionListener mListener;
    List<HashMap<String,String>> values=new ArrayList<>();

    public OneFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
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

        super.onCreate(savedInstanceState);
        String wordLength = new String("");

        if (getArguments() != null) {
           wordLength  = getArguments().getString("wordLength");
           if(wordLength == null)
               Toast.makeText(this.getContext(),"Type a 4 letter word", Toast.LENGTH_SHORT).show();
           else
               Toast.makeText(this.getContext(),"Type a " + wordLength + " letter word", Toast.LENGTH_SHORT).show();
        }

        if(wordLength != null && wordLength.equalsIgnoreCase("3"))
            str = new String[]{"cat","rat","kin","man","hen","nib","hat","mat","pat","bot","jet","lit","new","ten","mid","let","map","nap","fat","mix","pan","oar","nut","pub","ray","rod","rum","spy","yak","yam"};
        else if(wordLength != null && wordLength.equalsIgnoreCase("5"))
            str = new String[]{"jumbo","quack","jerky","crazy","proxy","spike","speak","mango","alter","amend","amids","anime","anode","amino","awful","beach","beast","beats","beard","beans","crawl","curse","dance","decaf","decoy","devil","equal","exist","exile","fancy"
                    ,"fairy","faith","fancy","fault","feast","flora","fetch","flats","frock","frost","germs","ghost","glove","graze","grope"};
        else
            str = new String[]{"hike","mike","mole","mile","lite","peak","pear","mine","shut","nose","bike","mild","lick","neat","vein","kith","nerd","word","zero"
                    ,"auto","hail","hair","bail","bait","bean","beam","beak","bind","blue","blur","born","pork","bunk"};
        //randomly select a value from the string array
        Random rn = new Random();
        int randomNo = rn.nextInt(20 );
        ltrToFind = str[randomNo];

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_one, container, false);
        typedWord = (EditText) view.findViewById(R.id.typedWord);
        typedWord.setFilters(new InputFilter[] {new InputFilter.LengthFilter(ltrToFind.length())});
        go_button = (ImageButton) view.findViewById(R.id.go_button);
        View header_view = view.findViewById(R.id.header_bar);
        if(values==null)
            header_view.setVisibility(View.GONE);
        //get the go button and disable it
        //go_button.setVisibility(View.GONE);
        go_button.setEnabled(false);

        ListView listView = (ListView)view.findViewById(R.id.customlist);
        listView.setItemsCanFocus(true);

        myAdapter= new TextViewAdapter_one(this.getContext(), values);
        listView.setAdapter(myAdapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            listView.setNestedScrollingEnabled(true);
        }

        ((MainActivity)getActivity()).setFragmentRefreshListener(new MainActivity.FragmentRefreshListener() {
            @Override
            public void onRefresh() {
                values.clear();
                myAdapter.notifyDataSetChanged();

                mListener.onFragmentInteraction("Reset2");
            }
        });

        typedWord.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                go_button.setVisibility(View.GONE);
                go_button.setEnabled(false);
                check = false;

                for(int i=0 ; i<s.length() ; i++)
                    for(int j=0 ; j<s.length() ; j++)
                        if(s.charAt(i) == s.charAt(j) && i!=j) {
                            typedWord.setError("Duplicate");
                            check = true;
                            break;
                        }
                if(s.length() == ltrToFind.length() && !check) {
                    go_button.setVisibility(View.VISIBLE);
                    go_button.setEnabled(true);
                }
            }
        });

        typedWord.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    goButtonClick();
                    return true;
                }
                return false;
            }
        });

        go_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goButtonClick();
            }
        });

        return view;
    }
    public void goButtonClick()
    {

        InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(typedWord.getWindowToken(), 0);

        bulls = cows = 0;
        ltrToFind = ltrToFind.toLowerCase();
        // Perform action on click
        String strEntered = typedWord.getText().toString().toLowerCase();
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
        if(bulls == 0 && cows == 0){
            mListener.onFragmentInteraction(strEntered);
        }

        //go_button.setVisibility(View.GONE);
        go_button.setEnabled(false);
        typedWord.setText("");

        if(bulls != ltrToFind.length()) {

            HashMap<String,String> value_entry = new HashMap();
            value_entry.put("entered_string",strEntered);
            value_entry.put("bulls",bulls+"");
            value_entry.put("cows",cows+"");

            values.add(0,value_entry);
            myAdapter.notifyDataSetChanged();

        }
        else { //once the word is found
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("You Found it")
                    .setTitle("Congrats");

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    //values.clear();
                    //myAdapter.notifyDataSetChanged();
                    //mListener.onFragmentInteraction("Reset2");
                }
            });
            builder.show();
        }
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String stringEntered);
    }

}
