package com.example.invotyx.olacontrols;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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

public class ScenesActivity extends Fragment {
    public static ScenesActivity newInstance() {
        ScenesActivity fragment = new ScenesActivity();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.scenes, container, false);


    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        DateTime dt = new DateTime();
        TextView textView = (TextView) view.findViewById(R.id.date_time);
        textView.setText(dt.getDateTime());

        showScenesFroAPI();


    }

    public void showScenesFroAPI()
    {
        final SceneViewAdapter sAdapter;
        final ArrayList<Scenes> sceneList = new ArrayList<>();
        final GridView gridView = getView().findViewById(R.id.scene_list);
        sAdapter = new SceneViewAdapter(getContext(), sceneList);

        IP ip = new IP();
        String URL = ip.getIP()+"network.cgi?jsongettitles=scenes";

        @SuppressLint("StaticFieldLeak") HttpGetRequest testTask =new HttpGetRequest(URL) {
            protected void onPostExecute(String result) {
                Log.i("Result",":"+result);
                if (result!=null) {
                    JSONArray jsonArr = null;
                    JSONObject jsonObj = null;
                    try {
                        jsonArr = new JSONArray(result);
                        for (int j = 0; j < jsonArr.length(); j++) {
                           // jsonObj = jsonArr.getJSONObject(j);
                            String sceneName = jsonArr.getString(j);
                            sAdapter.add(new Scenes( sceneName));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                sAdapter.add(new Scenes("Add Scene"));
                gridView.setAdapter(sAdapter);

            }

        }

                ;
        testTask.execute();


    }






}