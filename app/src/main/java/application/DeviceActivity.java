package application;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.invotyx.olacontrols.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapters.DeviceViewAdapter;
import adapters.RoomViewAdapter;
import api.HttpGetRequest;
import api.HttpPostRequest;
import api.IP;
import dataclasses.DateTime;
import dataclasses.Devices;
import dataclasses.Rooms;

public class DeviceActivity extends Fragment {
    RoomViewAdapter rAdapter;
    public static DeviceActivity newInstance() {
        DeviceActivity fragment = new DeviceActivity();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.device_fragment, container, false);





    }
    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {

        final TextView textView = (TextView) view.findViewById(R.id.date);
        DateTime dt = new DateTime();
        textView.setText(dt.getDateTime());

        /*final long period = 1000;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

            }
        }, 0, period);
*/


        showDevicesFromAPI();
        showRoomsFromAPI();
    }

    public void showDevicesFromAPI() {
        IP ip = new IP();
        String devicesUrl = ip.getIP()+"/network.cgi";
        String devicesBody = "json="+"{\"control\":{\"cmd\":\"getdevice\",\"uid\":256}}";
        final DeviceViewAdapter dAdapter;
        final ArrayList<Devices> deviceList = new ArrayList<>();
        final GridView gridView = (GridView) getView().findViewById(R.id.device_list);
        dAdapter = new DeviceViewAdapter(getContext(), deviceList);
        dAdapter.add(new Devices("", "Add device"));


        @SuppressLint("StaticFieldLeak") HttpPostRequest testTask =new HttpPostRequest(devicesUrl, devicesBody) {
            protected void onPostExecute(String result) {
                if (result!=null) {
                    // Toast.makeText(DeviceActivity.this, "Response: "+result, Toast.LENGTH_SHORT).show();
                    JSONArray jsonArr = null;
                    JSONObject jsonObj = null;
                    try {
                        final JSONObject obj = new JSONObject(result);
                        final JSONArray deviceArray = obj.getJSONArray("device");
                        for (int i = 0; i<deviceArray.length(); i++)
                        {
                            JSONObject device = deviceArray.getJSONObject(i);
                            final JSONArray channelArray = device.getJSONArray("channel");
                            int uid = device.getInt("uid");

                            for (int j = 0; j < channelArray.length(); j++) {
                                final JSONObject channel = channelArray.getJSONObject(j);
                                String channelName = channel.getString("name");
                                int funcType = channel.getInt("functype");
                                int channelId = channel.getInt("chid");
                                Log.i("Channel Name",":"+channelName);
                                //  Toast.makeText(DeviceActivity.this, "Device "+i+" : "+channelObject, Toast.LENGTH_SHORT).show();
                                if(funcType!=0)
                                {
                                    dAdapter.insert(new Devices("",funcType,channelId,uid),gridView.getChildCount());
                                   // dAdapter.insert(new Devices(funcType),gridView.getChildCount());

                                }

                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //  Toast.makeText(DeviceActivity.this, jsonArr.toString(), Toast.LENGTH_SHORT).show();
                }
               // dAdapter.add(new Devices("", "Add device"));
                gridView.setAdapter(dAdapter);



            }

        }

                ;
        testTask.execute();



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Intent myIntent = new Intent(getActivity(),
                        EditDeviceActivity.class);
                String device_name = null;
                int device_uid ;
                int device_chid;
                int device_func;

                ViewGroup gridChild = (ViewGroup) gridView.getChildAt(position);
                //int childSize = roomGridView.getChildCount();
                int count = gridView.getAdapter().getCount();
                if (position==count-1) {


                }
                else {

                    Devices device = (Devices) gridView.getAdapter().getItem(position);

                    device_name = device.getName();
                    device_chid = device.getChid();
                    device_uid = device.getUid();
                    device_func = device.getFunc_type();

                    //  Toast.makeText(getContext(), "" + room_name, Toast.LENGTH_SHORT).show();
                    myIntent.putExtra("deviceName", device_name);
                    myIntent.putExtra("deviceUid",device_uid);
                    myIntent.putExtra("deviceChid",device_chid);
                    myIntent.putExtra("deviceFuncType",device_func);
                    myIntent.putExtra("isNew","false");
                    myIntent.putExtra("deviceIcon",device.getIcon());
                    startActivity(myIntent);
                }
            }
        });


    }

    public void showRoomsFromAPI()
    {
        IP ip = new IP();
        String roomsUrl = ip.getIP()+
                "/network.cgi?jsongetgroup=rooms";

        final ArrayList<Rooms> roomList = new ArrayList<>();
        rAdapter = new RoomViewAdapter(getContext(), roomList);
        final GridView roomGridView = (GridView) getView().findViewById(R.id.room_list);
        rAdapter.add(new Rooms("", "Add Room"));

        @SuppressLint("StaticFieldLeak") HttpGetRequest testTask =new HttpGetRequest(roomsUrl) {
            protected void onPostExecute(String result) {
                if (result!=null) {
                    JSONArray jsonArr = null;
                    try {
                        jsonArr = new JSONArray(result);
                        for (int i = 0; i < jsonArr.length(); i++) {
                            JSONObject room = jsonArr.getJSONObject(i);
                            String roomName = room.getString("title");
                            String icon = room.getString("icon");
                          //  int icon_id = Integer.parseInt(icon);
                            Rooms room1 = new Rooms("",roomName);
                            rAdapter.insert(room1,roomGridView.getChildCount());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                roomGridView.setAdapter(rAdapter);


            }

        }

                ;
        testTask.execute();
        roomGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Intent myIntent = new Intent(getActivity(),
                        EditRoomActivity.class);
                String room_name = null;

                ViewGroup gridChild = (ViewGroup) roomGridView.getChildAt(position);
                //int childSize = roomGridView.getChildCount();
                int count = roomGridView.getAdapter().getCount();
                if (position==count-1) {

                    //instantiate the popup.xml layout file
                    LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View customView = layoutInflater.inflate(R.layout.edit_room_popup, null);

                    Button addBtn = customView.findViewById(R.id.update_room_title);
                    Button cancelBtn = customView.findViewById(R.id.cancel_update_room);
                    final EditText title = customView.findViewById(R.id.new_room_title);

                    //TextView.BufferType bf = null;
                    addBtn.setText("Add");
                    title.setHint("Room Title");

                    final PopupWindow popupWindow;
                    //instantiate popup window
                    popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    //display the popup window
                    popupWindow.showAtLocation(getView(), Gravity.CENTER, 0, 0);
                    popupWindow.setTouchable(true);
                    popupWindow.setFocusable(true);
                    popupWindow.setOutsideTouchable(false);
                    popupWindow.update();


                    popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {

                        }
                    } );
                    popupWindow.getContentView();

                    addBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String room_title = title.getText().toString();
                            rAdapter.insert(new Rooms("", room_title),roomGridView.getChildCount());
                            popupWindow.dismiss();
                            Intent myIntent = new Intent(getActivity(),
                                    EditRoomActivity.class);
                            myIntent.putExtra("roomName", room_title);
                            myIntent.putExtra("isNew","true");
                            startActivity(myIntent);


                        }
                    });
                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();

                        }

                    });

                }
                else {

                    TextView room = gridChild.findViewById(R.id.device_name);
                    room_name = room.getText().toString();
                  //  Toast.makeText(getContext(), "" + room_name, Toast.LENGTH_SHORT).show();
                    myIntent.putExtra("roomName", room_name);
                    myIntent.putExtra("isNew","false");
                    startActivity(myIntent);
                }
            }
        });


    }
    private View.OnClickListener roomClickListener = new View.OnClickListener() {
        public void onClick(View arg0) {



        }
    };
    private View.OnClickListener deviceClickListener = new View.OnClickListener() {
        public void onClick(View arg0) {



        }
    };



}