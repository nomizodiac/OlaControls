package application;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.invotyx.olacontrols.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapters.EditDeviceEventAdapter;
import adapters.EditDeviceRoomListAdapter;
import api.HttpGetRequest;
import api.HttpPostRequest;
import api.IP;
import dataclasses.Devices;
import dataclasses.Event;
import dataclasses.Rooms;
import dataclasses.Target;

public class EditDeviceActivity extends AppCompatActivity {

    private TextView deviceTitleView;
    private ImageView deviceIconView;
    private String deviceTitle;
    private int deviceUid;
    private int deviceChid;
    private int deviceFuncType;
    private int deviceIconId;
    private Devices mainDevice;
    private TextView deviceRoom;
    private TextView deviceEvent;
    private TextView deviceAssociate;
    private TextView deviceConfigure;
    private TextView deviceSecurity;
    private TextView deviceInfo;
    private TextView temp_C;
    private TextView temp_F;
    private Button deviceSaveBtn;
    private Button deviceDeleteBtn;
    private View root, blurView;
    private PopupWindow popupWindow;
    private ArrayList<Rooms> roomList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wait);
        Bundle extras = getIntent().getExtras();

        String is_new="";
        if (extras != null) {
            deviceTitle = extras.getString("deviceName");
            deviceUid = extras.getInt("deviceUid");
            deviceChid = extras.getInt("deviceChid");
            deviceFuncType = extras.getInt("deviceFuncType");
            deviceIconId = extras.getInt("deviceIcon");
            is_new = extras.getString("isNew");
        }
        getDeviceInformation();

    }

    private void onGettingResults()
    {
        if(deviceChid==11 && deviceFuncType==11)
        {
            setContentView(R.layout.edit_device_tempertaure);
            temp_C = findViewById(R.id.temp_centi);
            temp_F = findViewById(R.id.temp_fh);
            getTemperature();
        }
        else {
            setContentView(R.layout.edit_device);
        }
        root = findViewById(R.id.edit_device_root);
        deviceTitleView=findViewById(R.id.device_name);
        deviceIconView = findViewById(R.id.device_icon);
        deviceRoom = findViewById(R.id.device_room);
        deviceAssociate = findViewById(R.id.device_associate);
        deviceConfigure = findViewById(R.id.device_configure);
        deviceInfo = findViewById(R.id.device_info);
        deviceSecurity = findViewById(R.id.device_security);
        deviceEvent = findViewById(R.id.device_event);
        blurView = findViewById(R.id.edit_device_blur_view);

        deviceTitleView.setText(deviceTitle);
        deviceIconView.setImageResource(deviceIconId);
        showDeviceRoom();

        deviceTitleView.setOnClickListener(deviceTitleListener);
        deviceInfo.setOnClickListener(showDeviceInfoListener);
        deviceRoom.setOnClickListener(selectRoomListener);
        deviceConfigure.setOnClickListener(onConfigListener);
        deviceEvent.setOnClickListener(onEventListener);
        deviceSecurity.setOnClickListener(onSecurityListener);
        deviceAssociate.setOnClickListener(onAssociateListener);

    }

    private void showDeviceRoom()
    {

        String thisRoom = "None";
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
            homeID.setText(""+mainDevice.getHomeID());
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
        String deviceBody = "json="+"{\"control\":{\"cmd\":\"getdevice\",\"uid\":"+device_UID+"}}";


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

                String getInterfaceBody = "json="+"{\"control\":{\"cmd\":\"getdevice\",\"uid\":260}}";


                getHomeId();

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

    private void getTemperature()
    {

    }
    private void getHomeId()
    {
        IP ip = new IP();
        String deviceUrl = ip.getIP()+"/network.cgi";
        String getInterfaceBody = "json="+"{\"control\":{\"cmd\":\"getinterface\",\"uid\":260}}";


        @SuppressLint("StaticFieldLeak") HttpPostRequest testTask =new HttpPostRequest(deviceUrl, getInterfaceBody) {
            protected void onPostExecute(String result) {
                if (result!=null) {

                    JSONArray jsonArr = null;
                    JSONObject jsonObj = null;
                    try {
                        final JSONObject JSONresult = new JSONObject(result);
                        final JSONArray iface = JSONresult.getJSONArray("iface");
                        final JSONObject iface1 = iface.getJSONObject(0);
                        final JSONObject zwave = iface1.getJSONObject("zwave");
                        final String homeId  = zwave.getString("homeid");

                        mainDevice.setHomeID(homeId);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                getRooms();
            }

        }
        ;
        testTask.execute();
    }

    private JSONObject readConfig(String key)
    {

        final JSONObject config = new JSONObject();
        IP ip = new IP();
        String url = ip.getIP()+"/network.cgi";
        int device_UID = deviceUid;
        String body = "json="+"{\"control\":{\"cmd\":\"getconfig\",\"uid\":"+device_UID+",\"datas\":[{\"key\":\""+key+"\"}]}}";

        @SuppressLint("StaticFieldLeak") HttpPostRequest readConfigTask = new HttpPostRequest(url,body){
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if(result!=null)
                {
                    try {
                        JSONObject jo = new JSONObject(result);
                        JSONObject control = jo.getJSONObject("control");
                        JSONArray data = control.getJSONArray("datas");
                        JSONObject data1 = data.getJSONObject(0);
                        String value = data1.getString("val");
                        config.put("value",value);
                        String size = data1.getString("size");
                        config.put("size",size);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        readConfigTask.execute();

        return config;

    }
    private void writeConfig(String parameter, String value, String size)
    {


    }

    private void batteryNotify(int value)
    {
        IP ip = new IP();
        String url = ip.getIP()+"/network.cgi";
        int device_UID = deviceUid;
        String body = "json="+"{\"control\": {\"cmd\":\"setlowbattnotify\", \"uid\":"+device_UID+",\"val\":"+value+",\"chid\":0} }";

        @SuppressLint("StaticFieldLeak") HttpPostRequest notificationTask = new HttpPostRequest(url,body){
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if(result!=null)
                {

                }

            }
        };
        notificationTask.execute();
    }
    private void temperNotify(int value)
    {
        IP ip = new IP();
        String url = ip.getIP()+"/network.cgi";
        int device_UID = deviceUid;

        String body = "json="+"{\"control\": {\"cmd\":\"settampernotify\", \"uid\":"+device_UID+",\"val\":"+value+",\"chid\":0} }";

        @SuppressLint("StaticFieldLeak") HttpPostRequest notificationTask = new HttpPostRequest(url,body){
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if(result!=null)
                {

                }

            }
        };
        notificationTask.execute();

    }

    private void pirTriggerNotify(int value)
    {
        IP ip = new IP();
        String url = ip.getIP()+"/network.cgi";
        int device_UID = deviceUid;

        String body = "json="+"{\"control\": {\"cmd\":\"setmainscene\", \"uid\":"+device_UID+",\"val\":"+value+",\"chid\":0} }";

        @SuppressLint("StaticFieldLeak") HttpPostRequest notificationTask = new HttpPostRequest(url,body){
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if(result!=null)
                {

                }

            }
        };
        notificationTask.execute();
    }

    private void getEvents()
    {
        IP ip = new IP();
        String deviceUrl = ip.getIP()+"/network.cgi";
        int device_UID = deviceUid;
        String getEventBody = "json="+"{\"control\":{\"cmd\":\"getevent\",\"uid\":"+device_UID+"}}";



        @SuppressLint("StaticFieldLeak") HttpPostRequest testTask =new HttpPostRequest(deviceUrl, getEventBody) {
            protected void onPostExecute(String result) {
                final ArrayList<Event> deviceEvents = new ArrayList<>();
                if (result!=null) {

                    try {
                        JSONObject jo = new JSONObject(result);
                        JSONArray event = jo.getJSONArray("event");
                        for(int i = 0; i<event.length();i++)
                        {
                            JSONObject event1 = event.getJSONObject(i);
                            String eventTime = event1.getString("time");
                            String eventMsg = event1.getString("msg");

                            eventTime =eventTime.replace("-","/");

                            deviceEvents.add(new Event(eventTime,eventMsg));
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                showEvents(deviceEvents);
               // Log.i("EEEEEEEEEEEEEEEEEE",""+deviceEvents.get(0).getTime());
            }

        }
                ;
        testTask.execute();
    }
    private void showEvents(ArrayList<Event> events)
    {
        LayoutInflater layoutInflater = (LayoutInflater) EditDeviceActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.edit_device_events_popup, null);

        //instantiate popup window
        popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView dismissBtn = customView.findViewById(R.id.event_popup_dismiss);
        GridView eventList = customView.findViewById(R.id.eventpopup_eventList);

         EditDeviceEventAdapter eventAdapter = new EditDeviceEventAdapter(EditDeviceActivity.this,events);
         eventList.setAdapter(eventAdapter);

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

        dismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                blurView.setVisibility(View.GONE);
            }
        });


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
           showDeviceInfo();
        }

    };
    private View.OnClickListener selectRoomListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            LayoutInflater layoutInflater = (LayoutInflater) EditDeviceActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View customView = layoutInflater.inflate(R.layout.device_select_room_popup, null);

            ImageView updateBtn = customView.findViewById(R.id.room_ok_button);
            GridView room_list = customView.findViewById(R.id.room_list);


            ArrayList<Rooms> rList = roomList;

            EditDeviceRoomListAdapter rAdapter = new EditDeviceRoomListAdapter(EditDeviceActivity.this, rList);

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

    private View.OnClickListener onConfigListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LayoutInflater layoutInflater = (LayoutInflater) EditDeviceActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View customView = layoutInflater.inflate(R.layout.edit_device_configure_popup, null);


            final TextView c_parameter = customView.findViewById(R.id.config_parameter);
            final TextView c_value = customView.findViewById(R.id.config_value);
            final TextView c_size = customView.findViewById(R.id.config_size);
            Button readBtn = customView.findViewById(R.id.config_read_btn);
            Button writeBtn = customView.findViewById(R.id.config_write_btn);


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

            readBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String parameter = c_parameter.getText().toString();
                    if(parameter.equals(""))
                    {
                        Toast.makeText(EditDeviceActivity.this,"Please provide parameter value!",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        int value =0;
                        int size =0;
                        JSONObject config = readConfig(parameter);
                        try {
                            value = config.getInt("value");
                            size = config.getInt("size");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                       c_value.setText(""+value);
                       c_size.setText(""+size);
                    }
                }
            });

            writeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String parameter = c_parameter.getText().toString();
                    String value = c_value.getText().toString();
                    String size = c_value.getText().toString();
                    writeConfig(parameter,value,size);
                    popupWindow.dismiss();
                    blurView.setVisibility(View.GONE);
                }
            });

            //popupWindow.getContentView();



        }
    };

    private View.OnClickListener onEventListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getEvents();
        }
    };

    private View.OnClickListener onSecurityListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LayoutInflater layoutInflater = (LayoutInflater) EditDeviceActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


           if(deviceFuncType==13 || deviceFuncType==14)
           {
               View customView = layoutInflater.inflate(R.layout.edit_device_security_pir, null);
               //instantiate popup window
               popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
               //display the popup window
               popupWindow.showAtLocation(root, Gravity.CENTER, 0, 0);
               popupWindow.setTouchable(true);
               popupWindow.setFocusable(true);
               popupWindow.setOutsideTouchable(false);
               popupWindow.update();
               blurView.setVisibility(View.VISIBLE);


               final CheckBox battery_notify = customView.findViewById(R.id.pir_battery_notify);
               final CheckBox temper_notify = customView.findViewById(R.id.pir_temper_notify);
               final CheckBox trigger_notify = customView.findViewById(R.id.pir_allow_trigger);

               popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                   @Override
                   public void onDismiss() {

                       Boolean isBatteryNotify = battery_notify.isActivated();
                       Boolean isTemperNotify = temper_notify.isActivated();
                       Boolean isTriggerNotify = trigger_notify.isActivated();

                       if(isBatteryNotify)
                       {  batteryNotify(0);}
                       else
                       { batteryNotify(1);}


                       if(isTemperNotify)
                       { temperNotify(0);}
                       else
                       {temperNotify(1);}

                       if(isTriggerNotify)
                       { pirTriggerNotify(0);}
                       else
                       {pirTriggerNotify(1);}


                       blurView.setVisibility(View.GONE);

                   }
               });
           }
           else {
               View customView = layoutInflater.inflate(R.layout.edit_device_security, null);
               //instantiate popup window
               popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
               //display the popup window
               popupWindow.showAtLocation(root, Gravity.CENTER, 0, 0);
               popupWindow.setTouchable(true);
               popupWindow.setFocusable(true);
               popupWindow.setOutsideTouchable(false);
               popupWindow.update();
               blurView.setVisibility(View.VISIBLE);

               final CheckBox battery_notify = customView.findViewById(R.id.battery_notify);
               final CheckBox temper_notify = customView.findViewById(R.id.temper_notify);

               popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                   @Override
                   public void onDismiss() {

                       Boolean isBatteryNotify = battery_notify.isActivated();
                       Boolean isTemperNotify = temper_notify.isActivated();

                       if(isBatteryNotify)
                       {  batteryNotify(0);}
                       else
                       { batteryNotify(1);}



                       if(isTemperNotify)
                       { temperNotify(0);}
                       else
                       {temperNotify(1);}

                       blurView.setVisibility(View.GONE);
                   }
               });

           }
        }
    };

   private View.OnClickListener onAssociateListener = new View.OnClickListener() {
       @Override
       public void onClick(View v) {

           LayoutInflater layoutInflater = (LayoutInflater) EditDeviceActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           View customView = layoutInflater.inflate(R.layout.edit_device_associate_popup, null);



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
                   popupWindow.dismiss();
                   blurView.setVisibility(View.GONE);
               }
           } );


       }
   };



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

