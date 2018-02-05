package word.game.sudaraje.firstapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

public class TwoFragment extends android.support.v4.app.Fragment {

    public static ArrayList<Character> alc = new ArrayList<>();;
    public static TextViewAdapter myAdapter = null;

    public TwoFragment() {
        // Required empty public constructor
    }

    public void valueChange(String txtToBeDeleted){
        txtToBeDeleted = txtToBeDeleted.toLowerCase();

        System.out.println("before" + alc+"");
        for(int i=0 ; i<alc.size() ; i++)
            for(int j=0 ; j<txtToBeDeleted.length() ; j++)
                if(alc.get(i) == txtToBeDeleted.charAt(j))
                    alc.set(i,' ');
        System.out.println("after" + alc+"");

        if(txtToBeDeleted.equals("reset2"))
            setArrayList();

        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        GridView gridview = (GridView) view.findViewById(R.id.gridviewCustom);

        setArrayList();

        myAdapter = new TextViewAdapter(this.getContext(),alc);
        gridview.setAdapter(myAdapter);
        return view;
    }

    public void setArrayList(){
        String str = "abcdefghijklmnopqrstuvwxyz";
        alc.clear();
        for(int i=0 ;i<str.length() ; i++)
            alc.add(str.charAt(i));
    }

}