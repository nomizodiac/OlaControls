package application;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.example.invotyx.olacontrols.R;

public class MainActivity extends AppCompatActivity {

    ImageView devices, scenes, macros, settings;
    PopupWindow popupWindow;
    View root_view, blurView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen_new);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame, DeviceActivity.newInstance());
        transaction.commit();


        root_view = findViewById(R.id.mainscreen_root);
        devices = findViewById(R.id.device_icn);
        scenes = findViewById(R.id.scenes_icn);
        macros = findViewById(R.id.macros_icn);
        settings = findViewById(R.id.settings_icn);
        blurView = findViewById(R.id.main_screen_blur);
        setActiveColor(devices);


        devices.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_frame, DeviceActivity.newInstance());
                transaction.commit();
                setActiveColor(devices);
            }
        });
        macros.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_frame, MacrosActivity.newInstance());
                transaction.commit();
                setActiveColor(macros);
            }
        });
        scenes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_frame, ScenesActivity.newInstance());
                transaction.commit();
                setActiveColor(scenes);
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_frame, SettingsActivity.newInstance());
                transaction.commit();
                setActiveColor(settings);
            }
        });


    }

    @Override
    public void onBackPressed() {

       // super.onBackPressed();

        LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.exit_popup, null);



        //instantiate popup window
        popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //display the popup window
        popupWindow.showAtLocation(root_view, Gravity.CENTER, 0, 0);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.update();
        blurView.setVisibility(View.VISIBLE);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
                blurView.setVisibility(View.GONE);
            }
        } );


        Button exitBtn, cancelBtn;
        exitBtn = customView.findViewById(R.id.exit_exit_btn);
        cancelBtn = customView.findViewById(R.id.exit_cancel_btn);


        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                blurView.setVisibility(View.GONE);
            }
        });


    }

    public void setActiveColor(ImageView tab)
    {
        devices.setColorFilter(getResources().getColor(R.color.LoginLight));
        macros.setColorFilter(getResources().getColor(R.color.LoginLight));
        scenes.setColorFilter(getResources().getColor(R.color.LoginLight));
        settings.setColorFilter(getResources().getColor(R.color.LoginLight));

        if(tab==devices)
        {
            devices.setColorFilter(getResources().getColor(R.color.bg_grey));
        }
        else if(tab==macros)
        {
            macros.setColorFilter(getResources().getColor(R.color.bg_grey));
        }
        else if(tab==scenes)
        {
           scenes.setColorFilter(getResources().getColor(R.color.bg_grey));
        }
        else if(tab==settings)
        {
           settings.setColorFilter(getResources().getColor(R.color.bg_grey));
        }
    }

}
