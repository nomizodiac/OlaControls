package com.example.invotyx.olacontrols;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingsActivity extends Fragment {

    TextView userInfo, appInfo, gatewayInfo, otherSettings, options_, logOut;


    public static SettingsActivity newInstance() {
        SettingsActivity fragment = new SettingsActivity();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings, container, false);



    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        userInfo = getView().findViewById(R.id.user_info);
        appInfo = getView().findViewById(R.id.app_info);
        gatewayInfo = getView().findViewById(R.id.gateway_info);
        otherSettings = getView().findViewById(R.id.other_settings);
        options_ = getView().findViewById(R.id.options);
        logOut = getView().findViewById(R.id.logout);


    }

    public void onUserInfoClick()
    {

    }
    public void onGetewayInfoClick()
    {

    }
    public void onOtherSettingsClick()
    {

    }
    public void onOptionsClick()
    {

    }
    public void onAppInfoClick()
    {

    }
    public void onLogOutClick()
    {

    }

}