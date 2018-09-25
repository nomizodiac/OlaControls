package com.example.invotyx.olacontrols;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MacrosActivity extends Fragment {
    public static MacrosActivity newInstance() {
        MacrosActivity fragment = new MacrosActivity();
        return fragment;
    }
   //

    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.macros, container, false);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        DateTime dt = new DateTime();
        TextView textView = (TextView) view.findViewById(R.id.date_time);
        textView.setText(dt.getDateTime());

        showMacrosFromAPI();


       // statusIcon.setOnClickListener(onStatusIconClick);



    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        final ImageView statusIcon = getView().findViewById(R.id.macros_status);
        statusIcon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(statusIcon.equals(R.drawable.macro_on))
                {
                    statusIcon.setImageResource(R.drawable.macro_off);
                }
                else
                {
                    statusIcon.setImageResource(R.drawable.macro_on);
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void showMacrosFromAPI() {
        IP ip = new IP();
        String URL = ip.getIP()+"network.cgi?jsongetgroup=macros";


        final MacroViewAdapter mAdapter;
        final ArrayList<Macros> macroList = new ArrayList<>();
        final GridView gridView = getView().findViewById(R.id.macro_list);
        mAdapter = new MacroViewAdapter(getContext(), macroList);

        @SuppressLint("StaticFieldLeak") HttpGetRequest testTask =new HttpGetRequest(URL) {
            protected void onPostExecute(String result) {
                Log.i("Result",":"+result);
                if (result!=null) {
                    JSONArray jsonArr = null;
                    JSONObject jsonObj = null;
                    try {
                        jsonArr = new JSONArray(result);
                        for (int j = 0; j < jsonArr.length(); j++) {
                             jsonObj = jsonArr.getJSONObject(j);
                            String macroName = jsonObj.getString("title");
                            int macroStatus = jsonObj.getInt("enabled");
                            mAdapter.add(new Macros( macroName, macroStatus));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mAdapter.add(new Macros());
                gridView.setAdapter(mAdapter);

            }

        }

                ;
        testTask.execute();




    }



}
