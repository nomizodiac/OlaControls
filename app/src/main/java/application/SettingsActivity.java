package application;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.invotyx.olacontrols.R;

public class SettingsActivity extends Fragment {

    TextView userInfo, appInfo, gatewayInfo, otherSettings, options_, logOut;
    PopupWindow popupWindow = null;
    View root;
    View blurView;


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


        root = SettingsActivity.this.getView().getRootView();
        blurView = root.findViewById(R.id.main_screen_blur);
        userInfo = getView().findViewById(R.id.user_info);
        appInfo = getView().findViewById(R.id.app_info);
        gatewayInfo = getView().findViewById(R.id.gateway_info);
        otherSettings = getView().findViewById(R.id.other_settings);
        options_ = getView().findViewById(R.id.options);
        logOut = getView().findViewById(R.id.logout);

        userInfo.setOnClickListener(onUserInfoListener);
        appInfo.setOnClickListener(onAppInfoListener);
        gatewayInfo.setOnClickListener(onGatewayInfoListener);
        otherSettings.setOnClickListener(onOtherSettingsListener);
        options_.setOnClickListener(onOptionsListener);
        logOut.setOnClickListener(onLogoutListener);

    }

    private View.OnClickListener onAppInfoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View customView = getLayoutInflater().inflate(R.layout.settings_app_info, null);

            //instantiate popup window
            popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            //display the popup window
            popupWindow.showAtLocation(root, Gravity.CENTER, 0, 0);
            popupWindow.setTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(false);
            popupWindow.update();
            blurView.setVisibility(View.VISIBLE);

            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    blurView.setVisibility(View.GONE);
                }
            } );

        }
    };
    private View.OnClickListener onGatewayInfoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View customView = getLayoutInflater().inflate(R.layout.settings_gateway_info, null);

            //instantiate popup window
            popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            //display the popup window
            popupWindow.showAtLocation(root, Gravity.CENTER, 0, 0);
            popupWindow.setTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(false);
            popupWindow.update();
            blurView.setVisibility(View.VISIBLE);

            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    blurView.setVisibility(View.GONE);
                }
            } );


        }
    };

    private View.OnClickListener onUserInfoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View customView = getLayoutInflater().inflate(R.layout.settings_user_info_popup, null);

            //instantiate popup window
            popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            //display the popup window
            popupWindow.showAtLocation(root, Gravity.CENTER, 0, 0);
            popupWindow.setTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(false);
            popupWindow.update();
            blurView.setVisibility(View.VISIBLE);

            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    blurView.setVisibility(View.GONE);
                }
            } );


        }
    };

    private View.OnClickListener onOtherSettingsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View customView = getLayoutInflater().inflate(R.layout.settings_other_options_popup, null);

            //instantiate popup window
            popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            //display the popup window
            popupWindow.showAtLocation(root, Gravity.CENTER, 0, 0);
            popupWindow.setTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(false);
            popupWindow.update();
            blurView.setVisibility(View.VISIBLE);

            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    blurView.setVisibility(View.GONE);
                }
            } );


        }
    };

    private View.OnClickListener onOptionsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View customView = getLayoutInflater().inflate(R.layout.settings_options_popup, null);

            //instantiate popup window
            popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            //display the popup window
            popupWindow.showAtLocation(root, Gravity.CENTER, 0, 0);
            popupWindow.setTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(false);
            popupWindow.update();
            blurView.setVisibility(View.VISIBLE);

            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    blurView.setVisibility(View.GONE);
                }
            } );


        }
    };

    private View.OnClickListener onLogoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View customView = getLayoutInflater().inflate(R.layout.settings_logout_popup, null);

            //instantiate popup window
            popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            //display the popup window
            popupWindow.showAtLocation(root, Gravity.CENTER, 0, 0);
            popupWindow.setTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(false);
            popupWindow.update();
            blurView.setVisibility(View.VISIBLE);

            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    blurView.setVisibility(View.GONE);
                }
            } );


        }
    };


}