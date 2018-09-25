package com.example.invotyx.olacontrols;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class EditDeviceActivity extends AppCompatActivity {

    private TextView deviceTitleView;
    private String deviceTitle;
    private int deviceUid;
    private int deviceChid;
    private Devices mainDevice;
    private ImageView deviceIcon;
    private TextView deviceRoom;
    private TextView deviceEvent;
    private TextView deviceAssociate;
    private TextView deviceConfigure;
    private TextView deviceSecurity;
    private TextView deviceInfo;
    private Button deviceSaveBtn;
    private Button deviceDeleteBtn;
    private View root, blurView;
    private PopupWindow popupWindow;
    private ArrayList<Rooms> roomList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wait);
        getRooms();

    }


    private void onGettingResults()
    {
        setContentView(R.layout.edit_device);
        Bundle extras = getIntent().getExtras();

        String is_new="";
        if (extras != null) {
            deviceTitle = extras.getString("deviceName");
            deviceUid = extras.getInt("deviceUid");
            deviceChid = extras.getInt("deviceChid");
            is_new = extras.getString("isNew");
        }

        deviceTitleView=findViewById(R.id.device_name);
        deviceIcon = findViewById(R.id.device_icon);
        deviceRoom = findViewById(R.id.device_room);
        deviceAssociate = findViewById(R.id.device_associate);
        deviceConfigure = findViewById(R.id.device_configure);
        deviceInfo = findViewById(R.id.device_info);
        root = findViewById(R.id.edit_device_root);
        blurView = findViewById(R.id.edit_device_blur_view);

        deviceTitleView.setText(deviceTitle);
        showDeviceRoom();

        deviceTitleView.setOnClickListener(deviceTitleListener);
        deviceInfo.setOnClickListener(showDeviceInfoListener);
        deviceRoom.setOnClickListener(selectRoomListener);

    }

    private void showDeviceRoom()
    {

        String thisRoom = "Assign a Room";
        int SIZE = roomList.size();
        for(int i=0; i<SIZE; i++)
        {
            Rooms room1 = roomList.get(i);
            ArrayList<Target> targetList = room1.getTargets();
            for (int j=0; j<targetList.size(); j++)
            {
                Target target1 = targetList.get(j);
                if(target1.getTarget_chid()==deviceChid)
                {
                    if(target1.getTarget_devID()==deviceUid)
                    {
                        thisRoom = room1.getName();
                    }
                }
            }
        }

        deviceRoom.setText(thisRoom);
    }
    private void renameDevice(String new_title)
    {
        deviceTitleView.setText(new_title);
    }
    private void changeDeviceTitle(int new_icon_id)
    {
        deviceIcon = findViewById(new_icon_id);
    }
    private void chanageRoom(String assigned_room)
    {
        deviceRoom.setText(assigned_room);
    }
    private void showDeviceInfo()
    {

        //instantiate the popup.xml layout file
        LayoutInflater layoutInflater = (LayoutInflater) EditDeviceActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.device_info_popup, null);




        TextView uid = customView.findViewById(R.id.device_uid);
        TextView chid = customView.findViewById(R.id.device_chid);
        TextView homeID = customView.findViewById(R.id.device_homeID);
        TextView manufacturerID = customView.findViewById(R.id.device_manufacturer);
        TextView product = customView.findViewById(R.id.device_product);
        TextView productType = customView.findViewById(R.id.device_product_type);
        TextView subversion = customView.findViewById(R.id.device_subversion);
        TextView version = customView.findViewById(R.id.device_version);



        if(mainDevice!=null) {

            uid.setText( ""+mainDevice.getUid());
            chid.setText(""+mainDevice.getChid());
            homeID.setText("");
            manufacturerID.setText(""+mainDevice.getManufacture_id());
            product.setText(""+mainDevice.getProductId());
            productType.setText(""+mainDevice.getProductType());
            subversion.setText(""+mainDevice.getApplication_Subversion());
            version.setText(""+mainDevice.getApplication_Version());
        }



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
        popupWindow.getContentView();



    }


    private void getDeviceInformation()
    {
        IP ip = new IP();
        int device_UID = deviceUid;
        String deviceUrl = ip.getIP()+"/network.cgi";
        String deviceBody = "json="+"{\"control\":{\"cmd\":\"getdevice\",\"uid\":260}}";



        @SuppressLint("StaticFieldLeak") HttpPostRequest testTask =new HttpPostRequest(deviceUrl, deviceBody) {
            protected void onPostExecute(String result) {
                if (result!=null) {

                    Log.i("infoooooo",""+result);
                    JSONArray jsonArr = null;
                    JSONObject jsonObj = null;
                    try {
                        final JSONObject JSONresult = new JSONObject(result);
                        final JSONArray device = JSONresult.getJSONArray("device");
                        final JSONObject device1 = device.getJSONObject(0);

                        mainDevice = new Devices();
                        //TODO get device1 attributes here
                        mainDevice.setUid(device1.getInt("uid"));
                        mainDevice.setManufacture_id(device1.getInt("Manufacture ID"));
                        mainDevice.setProductId(device1.getInt("Product ID"));
                        mainDevice.setProductType(device1.getInt("Product type"));
                        mainDevice.setApplication_Subversion(device1.getInt("Application_Subversion"));
                        mainDevice.setApplication_Version(device1.getInt("Application_Version"));
                        mainDevice.setChid(deviceChid);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                Log.i("Device Name",""+mainDevice.getName());

                showDeviceInfo();
            }

        }

                ;
        testTask.execute();
    }


    private void getRooms()
    {
        IP ip = new IP();
        String roomsUrl = ip.getIP()+
                "/network.cgi?jsongetgroup=rooms";

        roomList = new ArrayList<>();

        @SuppressLint("StaticFieldLeak") HttpGetRequest testTask =new HttpGetRequest(roomsUrl) {
            protected void onPostExecute(String result) {
                if (result!=null) {

                    //   Toast.makeText(DeviceActivity.this, "Response: "+result, Toast.LENGTH_SHORT).show();
                    JSONArray jsonArr = null;
                    try {
                        jsonArr = new JSONArray(result);
                        for (int i = 0; i < jsonArr.length(); i++) {
                            JSONObject room1 = jsonArr.getJSONObject(i);
                            String roomTitle =room1.getString("title");
                            Rooms room = new Rooms(""+(i+1), roomTitle);
                            JSONArray targets = room1.getJSONArray("targets");

                            ArrayList<Target> targetList = new ArrayList<>();
                            for (int j=0; j<targets.length();j++)
                            {
                                Target target1 = new Target();
                                JSONObject target = targets.getJSONObject(j);
                                int chid = target.getInt("ch");
                                int devid = target.getInt("dev");
                                int if_ = target.getInt("if");

                                target1.setTarget_chid(chid);
                                target1.setTarget_devID(devid);
                                target1.setTarget_if(if_);

                                targetList.add(target1);
                            }
                            room.setTargets(targetList);
                            roomList.add(room);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    onGettingResults();
                }





            }

        };
        testTask.execute();
    }

    private View.OnClickListener deviceTitleListener = new View.OnClickListener()
    {
        public void onClick(View arg0)
        {
            //instantiate the popup.xml layout file
            LayoutInflater layoutInflater = (LayoutInflater) EditDeviceActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View customView = layoutInflater.inflate(R.layout.device_rename_popup, null);


            ImageView updateBtn = customView.findViewById(R.id.ok_button);
            final EditText title = customView.findViewById(R.id.new_device_title);

            //instantiate popup window
            popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            //display the popup window
            popupWindow.showAtLocation(root, Gravity.CENTER, 0, 0);
            popupWindow.setTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(false);
            popupWindow.update();
            blurView.setVisibility(View.VISIBLE);
            String current_title = deviceTitleView.getText().toString();
            title.setText(current_title);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    blurView.setVisibility(View.GONE);
                }
            } );
            popupWindow.getContentView();

            updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String new_title = title.getText().toString();
                    if(new_title.equals(""))
                    {
                        Toast.makeText(EditDeviceActivity.this,"Title cannot be empty.!",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        renameDevice(new_title);
                        popupWindow.dismiss();
                        blurView.setVisibility(View.GONE);
                    }
                }
            });


        }

    };
    private View.OnClickListener showDeviceInfoListener = new View.OnClickListener()
    {
        public void onClick(View arg0)
        {
           getDeviceInformation();
        }

    };
    private View.OnClickListener selectRoomListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            LayoutInflater layoutInflater = (LayoutInflater) EditDeviceActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View customView = layoutInflater.inflate(R.layout.device_select_room_popup, null);

            ImageView updateBtn = customView.findViewById(R.id.room_ok_button);
            ListView room_list = customView.findViewById(R.id.room_list);
            EditDeviceRoomListAdapter rAdapter = new EditDeviceRoomListAdapter(getApplicationContext(),roomList);

            for(int i=0; i<roomList.size(); i++)
            {
                Rooms room1 = roomList.get(i);
                rAdapter.add(room1);
            }
            rAdapter.add(new Rooms("-1","None"));
            room_list.setAdapter(rAdapter);

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
            popupWindow.getContentView();

            updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                     popupWindow.dismiss();
                        blurView.setVisibility(View.GONE);

                }
            });


        }
    };

}

