package com.example.invotyx.olacontrols;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class EditRoomActivity extends AppCompatActivity {


    private GridView addDeviceGridView, availableDevicesGrid;
    private SwitchCompat roomSwitch;
    private String room_icon_name;
    private AvailableDevicesAdapter addButtonAdapter;
    private AvailableDevicesAdapter mAdapter;
    private AvailableDevicesAdapter addDeviceAdapter;
    private RelativeLayout availableDeviceView;
    private TextView room_name_view;
    private String room_name;
    private String old_room_name;
    private ImageView delete_room;
    private ImageView save_room;
    private static Devices allDevices[];
    private static Devices unavailableDevices[];
    private static Devices availableDevices[];
    private static Devices roomDevices[];
    private static Boolean allDev, unavailDev, availDev, roomDev ;
    private FrameLayout root_view;
    private PopupWindow popupWindow;
    private FrameLayout blur_bg;
    private Boolean isChanged;
    private Boolean isTitleChanged, isDeviceUpdated;
    Boolean isNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_room);

        Bundle extras = getIntent().getExtras();

        String is_new="";
        if (extras != null) {
            room_name = extras.getString("roomName");
            is_new = extras.getString("isNew");
        }
        addDeviceGridView = findViewById(R.id.add_device);
        availableDevicesGrid = findViewById(R.id.available_devices_grid);
        roomSwitch = findViewById(R.id.room_switch);
        ImageView room_icon = findViewById(R.id.room_icon);
        room_icon_name = (String) room_icon.getTag();
        room_name_view = findViewById(R.id.room_name);
        room_name_view.setText(room_name);
        old_room_name = room_name;
        availableDeviceView = findViewById(R.id.available_devices_view);
        save_room =findViewById(R.id.save_room);
        delete_room = findViewById(R.id.delete_room);
        root_view = findViewById(R.id.edit_room_root);
        blur_bg = findViewById(R.id.blur_view);
        isChanged= false;
        isTitleChanged = false;
        isDeviceUpdated = false;
        allDev = false;
        unavailDev = false;
        availDev = false;
        roomDev =false;

        if(is_new.equals("true"))
        {
            isChanged=true;
            isDeviceUpdated = true;
        }

        getAllDevices();

        room_name_view.setOnClickListener(roomTitleListener);
        delete_room.setOnClickListener(deleteBtnListsener);
        save_room.setOnClickListener(saveBtnListener);
    }

    public void showRoomDevices() {
        final ArrayList<Devices> deviceList = new ArrayList<>();
        addDeviceAdapter = new AvailableDevicesAdapter(EditRoomActivity.this, deviceList){
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                View listItem = convertView;
                if(listItem == null) {
                    listItem = LayoutInflater.from(mContext).inflate(R.layout.macros_cell, parent, false);
                }
                Devices currentDevice = deviceList.get(position);
                TextView deviceName = (TextView) listItem.findViewById(R.id.macro_name);
                ImageView deviceIcon = (ImageView) listItem.findViewById(R.id.macro_icon);
                ImageView addIcon = listItem.findViewById(R.id.macros_status);
                deviceName.setText(currentDevice.getName());
                deviceIcon.setImageResource(currentDevice.getIcon());
                addIcon.setImageResource(R.drawable.macro_cross);
                if(currentDevice.getFunc_type()==-0)
                {
                    //listItem = LayoutInflater.from(mContext).inflate(R.layout.add_cell_rounded, parent, false);
                    deviceName.setText("Add New");
                    deviceIcon.setImageResource(R.drawable.macro_add);
                    addIcon.setVisibility(View.GONE);
                }

                return listItem;
            }

        };


        if(allDevices!=null && roomDevices!=null) {

            for(int i =0; i<roomDevices.length;i++) {
                int channeid = roomDevices[i].getChid();
                for (int k = 0; k < allDevices.length; k++) {
                    if (allDevices[k].getChid() == channeid) {
                        Log.i("ch id", "" + channeid);
                        if (allDevices[k].getFunc_type() != 0) {
                            addDeviceAdapter.add(allDevices[k]);
                        }

                    }

                }
            }


            }


        addDeviceGridView.setAdapter(addDeviceAdapter);
        addDeviceAdapter.add(new Devices(-0));

        addDeviceGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                getAvailableDevices();

                if (position==addDeviceGridView.getChildCount()-1) {

                } else {

                    mAdapter.add(addDeviceAdapter.getItem(position));
                    addDeviceAdapter.remove(addDeviceAdapter.getItem(position));
                    isChanged = true;
                    isDeviceUpdated = true;

                }

            }
        });


    }

    public void showAvailableDevices() {

        availableDeviceView.setVisibility(View.VISIBLE);
        final ArrayList<Devices> deviceList = new ArrayList<>();
        mAdapter = new AvailableDevicesAdapter(EditRoomActivity.this, deviceList){
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                View listItem = convertView;
                if(listItem == null) {
                    listItem = LayoutInflater.from(mContext).inflate(R.layout.macros_cell, parent, false);
                }
                Devices currentDevice = deviceList.get(position);
                TextView deviceName = (TextView) listItem.findViewById(R.id.macro_name);
                ImageView deviceIcon = (ImageView) listItem.findViewById(R.id.macro_icon);
                ImageView addIcon = listItem.findViewById(R.id.macros_status);
                deviceName.setText(currentDevice.getName());
                deviceIcon.setImageResource(currentDevice.getIcon());
                addIcon.setImageResource(R.drawable.macro_add);
                return listItem;
            }

        };


            if(availableDevices!=null)
            {

                for(int i=0; i< availableDevices.length; i++)
                {
                    mAdapter.add(availableDevices[i]);
                }
            }

        availableDevicesGrid.setAdapter(mAdapter);
        availableDevicesGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                addDeviceAdapter.insert(mAdapter.getItem(position), addDeviceGridView.getAdapter().getCount() - 1);
                mAdapter.remove(mAdapter.getItem(position));
                isChanged = true;
                isDeviceUpdated = true;

            }
        });

    }


    public void editRoomName(final String newName)
    {
        IP ip = new IP();
        String roomsUrl = ip.getIP()+
                "/network.cgi?jsongettitles=rooms";


        if(newName=="")
        {
            Toast.makeText(EditRoomActivity.this,"Title cannot be Empty.!",Toast.LENGTH_SHORT).show();
            isTitleChanged=false;
            return;
        }
        @SuppressLint("StaticFieldLeak") HttpGetRequest testTask =new HttpGetRequest(roomsUrl) {
            protected void onPostExecute(String result) {
                if (result!=null) {
                    Boolean isValid=true;
                    JSONArray jsonArr = null;
                    try {
                        jsonArr = new JSONArray(result);
                        for (int i = 0; i < jsonArr.length(); i++) {
                            String roomName = jsonArr.getString(i);
                            if(newName.equals(roomName))
                            {
                                isValid=false;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(isValid)
                    {
                        room_name_view.setText(newName);
                        isTitleChanged=true;
                    }
                    else
                    {

                        Toast.makeText(EditRoomActivity.this,"A edit_room with this title already exists.!",Toast.LENGTH_SHORT).show();
                        isTitleChanged=false;
                    }
                }

            }

        };
        testTask.execute();



    }

    private void deleteRoom(String roomTitle)
    {

        IP ip = new IP();

        String deleteRoomUrl = ip.getIP()+"/network.cgi?jsondel="+"{\"rooms\":[{\"title\":\""+roomTitle+"\"}]}";
        @SuppressLint("StaticFieldLeak") HttpGetRequest testTask =new HttpGetRequest(deleteRoomUrl) {
            protected void onPostExecute(String result) {
                if(result.equals("jsondel done"))
                {
                    Toast.makeText(EditRoomActivity.this,"Room deleted..!",Toast.LENGTH_SHORT).show();
                }
                else
                {

                    Toast.makeText(EditRoomActivity.this,"Delete unsuccessful..!",Toast.LENGTH_SHORT).show();
                }


            }

        }

                ;
        testTask.execute();

    }
    private void saveRoom()
    {
        if(isChanged) {
            if (isDeviceUpdated) {

                 JSONArray targets= new JSONArray();
                int count = addDeviceAdapter.getCount()-1;
                for(int i=0; i<count; i++)
                {
                    JSONObject jo = new JSONObject();
                    int chid = addDeviceAdapter.getItem(i).getChid();
                    int devId= addDeviceAdapter.getItem(i).getUid();
                    int if_ = 256;
                    try {
                        jo.put("ch", chid);
                        jo.put("dev", devId);
                        jo.put("if",if_);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    targets.put(jo);
                }

                JSONArray extra_camera = new JSONArray();
                String icon_name = "";
                String room_title = old_room_name;
                JSONObject room1 = new JSONObject();
                JSONArray room_array = new JSONArray();
                JSONObject rooms = new JSONObject();
                 try {
                     room1.put("camera","");
                     room1.put("camera_password", "");
                    // room1.put("extra_camera",extra_camera);
                     room1.put("icon","");
                     room1.put("icon_name",icon_name);
                     room1.put("targets",targets);
                     room1.put("title",room_title);
                     room_array.put(room1);
                     rooms.put("rooms",room_array);
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }

                Log.i("rrrrrrrrrrrrrrr",""+rooms);

                IP ip = new IP();
                String changeTitleUrl = ip.getIP()+"/network.cgi?jsonsetall=" + rooms;
                @SuppressLint("StaticFieldLeak") HttpGetRequest testTask = new HttpGetRequest(changeTitleUrl) {
                    protected void onPostExecute(String result) {
                        if (result !=null) {

                            if(result.equals("jsonsetall done"))
                            {
                                Toast.makeText(EditRoomActivity.this, "Changes have been saved successfully", Toast.LENGTH_SHORT).show();
                            }
                            else if(result.equals("jsonsetall failed"))
                            {
                                Toast.makeText(EditRoomActivity.this, "Update failed..!", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(EditRoomActivity.this, "Cannot update changes..!", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                };
                testTask.execute();

                 isChanged=false;
            }
            if (isTitleChanged) {
                String old_title, new_title;
                old_title = old_room_name;
                new_title = room_name_view.getText().toString();
                IP ip = new IP();
                String changeTitleUrl = ip.getIP() + "/network.cgi?jsonrename=[\"rooms\",\"" + old_title + "\",\"" + new_title + "\"]";
                @SuppressLint("StaticFieldLeak") HttpGetRequest testTask = new HttpGetRequest(changeTitleUrl) {
                    protected void onPostExecute(String result) {
                        if (result !=null) {

                            if(result.equals("jsonrename done"))
                            {

                                Toast.makeText(EditRoomActivity.this, "Title changed successfully", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {

                                Toast.makeText(EditRoomActivity.this, "Can not change title", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                };
                testTask.execute();


                isChanged=false;
            }


        }


    }


    @Override
    public void onBackPressed() {
        if(isChanged)
        {
            //instantiate the popup.xml layout file
            LayoutInflater layoutInflater = (LayoutInflater) EditRoomActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View customView = layoutInflater.inflate(R.layout.save_room_popup, null);

            Button saveBtn = customView.findViewById(R.id.save_changes);
            Button discardBtn = customView.findViewById(R.id.cancel_changes);
            discardBtn.setText("Discard");

            //instantiate popup window
            popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            //display the popup window
            popupWindow.showAtLocation(root_view, Gravity.CENTER, 0, 0);
            popupWindow.setTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(false);
            popupWindow.update();
            blur_bg.setVisibility(View.VISIBLE);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    blur_bg.setVisibility(View.GONE);
                }
            } );
            //close the popup window on button click
            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveRoom();
                    popupWindow.dismiss();
                    blur_bg.setVisibility(View.GONE);
                    EditRoomActivity.super.onBackPressed();

                }

            });
            discardBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    blur_bg.setVisibility(View.GONE);
                    EditRoomActivity.super.onBackPressed();

                }

            });

        }
        else
        {
            super.onBackPressed();
            Intent myIntent = new Intent(EditRoomActivity.this,
                    MainActivity.class);
            startActivity(myIntent);

        }


    }


    public void getAllDevices()
    {

        final Devices[][] deviceSet = {null};
        IP ip = new IP();
        String devicesUrl = ip.getIP()+"/network.cgi";
        String devicesBody = "json="+"{\"control\":{\"cmd\":\"getdevice\",\"uid\":256}}";

        @SuppressLint("StaticFieldLeak") HttpPostRequest testTask =new HttpPostRequest(devicesUrl, devicesBody) {
            protected void onPostExecute(String result) {
                Log.i("Result", ":" + result);
                try {
                    final JSONObject obj = new JSONObject(result);
                    final JSONArray deviceArray = obj.getJSONArray("device");
                    int s=0;
                    Devices tempDev[] = new Devices[1000];
                    for (int i = 0; i < deviceArray.length(); i++) {
                        JSONObject device = deviceArray.getJSONObject(i);
                        final JSONArray channelArray = device.getJSONArray("channel");
                        int uid = device.getInt("uid");


                        for (int j = 0; j < channelArray.length(); j++) {

                            final JSONObject channel = channelArray.getJSONObject(j);
                            String channelName = channel.getString("name");
                            int funcType = channel.getInt("functype");
                            int channelId = channel.getInt("chid");

                            Log.i("Channel ID-----", "-" + channelId);
                            if(funcType!=0)
                            {
                                Devices d = new Devices("",funcType,channelId,uid);
                                tempDev[s]=d;
                                s++;
                            }

                        }

                    }
                    int SIZE =s;
                    deviceSet[0] = new Devices[SIZE];
                    for(int h=0;h<SIZE;h++)
                    {
                        deviceSet[0][h]=tempDev[h];

                    }


                    Log.i("AllDEV SIZE;;;;",""+ deviceSet[0].length);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

               EditRoomActivity.setAllDevices(deviceSet[0]);
                Log.i("AllDevices length",""+deviceSet[0].length);
                getRoomDevices(getRoom_name());

            }

        };
        testTask.execute();


    }

    public void getRoomDevices(final String room_title)
    {

        final Devices d[] = this.allDevices;
        final Devices[][] deviceSet = {null};

        if(d!=null) {
            final Devices tempD[]= new Devices[1000];

            IP ip = new IP();
            String rDevicesUrl = ip.getIP() +
                    "/network.cgi?jsongetall";

            final ArrayList<Devices> devList = new ArrayList<>();

            @SuppressLint("StaticFieldLeak") HttpGetRequest testTask = new HttpGetRequest(rDevicesUrl) {
                protected void onPostExecute(String result) {
                    if (result != null) {
                        JSONObject jsonObject = null;
                        JSONArray jsonArr = null;
                        try {
                            int SIZE =0;
                            jsonObject = new JSONObject(result);
                            jsonArr = jsonObject.getJSONArray("rooms");
                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject room = jsonArr.getJSONObject(i);
                                String room_name = room.getString("title");
                                Log.i("Room name", "" + room_name);
                                if (room_name.equals(room_title)) {
                                    JSONArray r_devices = room.getJSONArray("targets");
                                    for (int j = 0; j < r_devices.length(); j++) {
                                        JSONObject dev = r_devices.getJSONObject(j);
                                        int channeid = dev.getInt("ch");
                                        Log.i("ch id " + j, "" + channeid);
                                        for (int k = 0; k < d.length; k++) {

                                            if (d[k].getChid() == channeid) {
                                                Log.i("ch id", "" + channeid);
                                                if(d[k].getFunc_type()!=0)
                                                {
                                                    tempD[SIZE]=d[k];
                                                    SIZE++;
                                                }

                                            }

                                        }

                                    }


                                }

                            }
                            deviceSet[0] = new Devices[SIZE];
                            for(int i=0; i<SIZE; i++)
                            {
                                deviceSet[0][i]=tempD[i];
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                   EditRoomActivity.setRoomDevices(deviceSet[0]);
                    Log.i("Ndevices",""+deviceSet[0].length);
                    showRoomDevices();
                    getUnavailableDevices();
                }

            };
            testTask.execute();

        }

    }
    public void getUnavailableDevices()
    {

        final Devices d[] = EditRoomActivity.allDevices;
        final Devices[][] deviceSet = {null};
        if(d!=null) {

            IP ip = new IP();
            String rDevicesUrl = ip.getIP() +
                    "/network.cgi?jsongetall";
            @SuppressLint("StaticFieldLeak") HttpGetRequest testTask = new HttpGetRequest(rDevicesUrl) {
                protected void onPostExecute(String result) {
                    if (result != null) {
                        JSONObject jsonObject = null;
                        JSONArray jsonArr = null;
                        try {
                            jsonObject = new JSONObject(result);
                            int channelId[]= new int[1000];
                            int uid[]= new int[1000];
                            int s=0;
                            jsonArr = jsonObject.getJSONArray("rooms");

                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject room = jsonArr.getJSONObject(i);

                                JSONArray r_devices = room.getJSONArray("targets");
                                for (int j = 0; j < r_devices.length(); j++) {
                                    JSONObject dev = r_devices.getJSONObject(j);
                                    int channeid = dev.getInt("ch");
                                    int u_id = dev.getInt("dev");
                                    channelId[s]=channeid;
                                    uid[s]=u_id;
                                            s++;
                                }
                            }

                            deviceSet[0] = new Devices[s];
                           int ss=0;
                            for(int j=0; j<d.length;j++)
                            {
                                Devices currentDevice = d[j];
                                for(int k=0; k<s; k++)
                                if(channelId[k]==currentDevice.getChid()&& uid[k]==currentDevice.getUid())
                                {
                                    deviceSet[0][ss]=currentDevice;
                                    ss++;

                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        deviceSet[0]=new Devices[0];
                    }

                  EditRoomActivity.setUnavailableDevices(deviceSet[0]);

                }

            };
            testTask.execute();
        }

    }
    public void getAvailableDevices()
    {
        Devices d[] = allDevices;
        Devices ud[] = unavailableDevices;
        Devices deviceSet[] ;

        if(d!=null)
        {
            if(ud==null)
            {
                int SIZE = d.length;
                deviceSet = new Devices[SIZE];
                for (int j = 0; j < SIZE; j++) {
                    deviceSet[j]=d[j];
                }
            }
            else
            {
                int size = d.length-ud.length;
                deviceSet = new Devices[size];

                //TODO
                int s=0;

                for (int i = 0; i < d.length; i++) {
                    Boolean isAvailable = true;
                    Devices d1 = d[i];
                    for(int j=0; j< ud.length; j++)
                    {
                        Devices ud1 = ud[j];
                        if(ud1.getUid()==d1.getUid()&&ud1.getChid()==d1.getChid())
                        {
                            isAvailable = false;
                            break;
                        }

                    }
                    if(isAvailable)
                    {
                        deviceSet[s]=d1;
                        s++;
                    }

                }

            }

        }
        else
        {
            deviceSet = new Devices[0];
        }


        EditRoomActivity.setAvailableDevices(deviceSet);
         showAvailableDevices();

    }

    public static void setAllDevices(Devices[] allDevices) {
        EditRoomActivity.allDevices = allDevices;
        Log.i("setAll ",""+allDevices.length);
        EditRoomActivity.allDev=true;
    }

    public static void setAvailableDevices(Devices[] availableDevices) {
        EditRoomActivity.availableDevices = availableDevices;

    }

    public static void setUnavailableDevices(Devices[] unavailableDevices) {
        EditRoomActivity.unavailableDevices = unavailableDevices;
        Log.i("setUnavailable ",""+unavailableDevices.length);
        EditRoomActivity.unavailDev=true;
    }

    public static void setRoomDevices(Devices[] roomDevices) {
        EditRoomActivity.roomDevices = roomDevices;
        Log.i("setRoom ",""+roomDevices.length);
        EditRoomActivity.roomDev=true;
    }

    public String getRoom_name() {
        return room_name;
    }

    private View.OnClickListener roomTitleListener = new View.OnClickListener()
    {
        public void onClick(View arg0)
        {
            //instantiate the popup.xml layout file
            LayoutInflater layoutInflater = (LayoutInflater) EditRoomActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View customView = layoutInflater.inflate(R.layout.edit_room_popup, null);

            Button updateBtn = customView.findViewById(R.id.update_room_title);
            Button cancelBtn = customView.findViewById(R.id.cancel_update_room);
            final EditText title = customView.findViewById(R.id.new_room_title);

            //instantiate popup window
            popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            //display the popup window
            popupWindow.showAtLocation(root_view, Gravity.CENTER, 0, 0);
            popupWindow.setTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(false);
            popupWindow.update();
            blur_bg.setVisibility(View.VISIBLE);
            title.setText(getRoom_name());
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    blur_bg.setVisibility(View.GONE);
                }
            } );
            popupWindow.getContentView();

            updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String new_title = title.getText().toString();
                    if(new_title.equals(""))
                    {
                        Toast.makeText(EditRoomActivity.this,"Title cannot be empty.!",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        editRoomName(new_title);
                        popupWindow.dismiss();
                        blur_bg.setVisibility(View.GONE);
                        blur_bg.bringToFront();
                        isChanged = true;
                    }
                }
            });
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    blur_bg.setVisibility(View.GONE);
                }

            });
        }
    };
    private View.OnClickListener deleteBtnListsener = new View.OnClickListener() {
        public void onClick(View arg0) {
            //instantiate the popup.xml layout file
            LayoutInflater layoutInflater = (LayoutInflater) EditRoomActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View customView = layoutInflater.inflate(R.layout.delete_room_popup, null);

            Button deleteBtn = customView.findViewById(R.id.delete_room);
            Button cancelBtn = customView.findViewById(R.id.cancel_delete_room);

            //instantiate popup window
            popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            //display the popup window
            popupWindow.showAtLocation(root_view, Gravity.CENTER, 0, 0);
            popupWindow.setTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(false);
            popupWindow.update();
            blur_bg.setVisibility(View.VISIBLE);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    blur_bg.setVisibility(View.GONE);
                }
            } );
            //close the popup window on button click
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    deleteRoom(room_name);
                    blur_bg.setVisibility(View.GONE);
                    Intent myIntent = new Intent(EditRoomActivity.this,
                            MainActivity.class);
                    startActivity(myIntent);
                }

            });
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    blur_bg.setVisibility(View.GONE);
                }

            });

        }
    };
    private View.OnClickListener saveBtnListener = new View.OnClickListener() {
        public void onClick(View arg0) {
            //instantiate the popup.xml layout file
            LayoutInflater layoutInflater = (LayoutInflater) EditRoomActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View customView = layoutInflater.inflate(R.layout.save_room_popup, null);

            Button saveBtn = customView.findViewById(R.id.save_changes);
            Button cancelBtn = customView.findViewById(R.id.cancel_changes);

            //instantiate popup window
            popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            //display the popup window
            popupWindow.showAtLocation(root_view, Gravity.CENTER, 0, 0);
            popupWindow.setTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(false);
            popupWindow.update();
            blur_bg.setVisibility(View.VISIBLE);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    blur_bg.setVisibility(View.GONE);
                }
            } );
            //close the popup window on button click
            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveRoom();
                    popupWindow.dismiss();
                    blur_bg.setVisibility(View.GONE);

                }

            });
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    blur_bg.setVisibility(View.GONE);
                }

            });


        }
    };

}
