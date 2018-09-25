package com.example.invotyx.olacontrols;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DeviceFragment extends Fragment {
    public static DeviceFragment newInstance() {
        DeviceFragment fragment = new DeviceFragment();
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd MMM yyyy  |  hh:mm:ss a");
        String strDate = mdformat.format(calendar.getTime());
        TextView textView = (TextView) view.findViewById(R.id.date);
        textView.setText(strDate);

        showDevicesFromAPI();
        showRoomsFromAPI();
    }

    public void showDevicesFromAPI() {
        String devicesUrl = "http://192.168.100.12/network.cgi";
        String devicesBody = "json="+"{\"control\":{\"cmd\":\"getdevice\",\"uid\":256}}";

        final DeviceViewAdapter dAdapter;
        final ArrayList<Devices> deviceList = new ArrayList<>();
        final GridView gridView = (GridView) getView().findViewById(R.id.device_list);
        dAdapter = new DeviceViewAdapter(getContext(), deviceList);


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

                            for (int j = 0; j < channelArray.length(); j++) {
                                final JSONObject channel = channelArray.getJSONObject(j);
                                String channelName = channel.getString("name");
                                int funcType = channel.getInt("functype");
                                Log.i("Channel Name",":"+channelName);
                                //  Toast.makeText(DeviceActivity.this, "Device "+i+" : "+channelObject, Toast.LENGTH_SHORT).show();
                                if(funcType!=0)
                                    dAdapter.add(new Devices(funcType));
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //  Toast.makeText(DeviceActivity.this, jsonArr.toString(), Toast.LENGTH_SHORT).show();
                }
                dAdapter.add(new Devices("", "Add device"));
                gridView.setAdapter(dAdapter);


            }

        }

                ;
        testTask.execute();


/*

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(DeviceActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

*/

    }

    public void showRoomsFromAPI()
    {
        String roomsUrl = "http://192.168.100.12" +
                "/network.cgi?jsongettitles=rooms";
        final RoomViewAdapter rAdapter;
        final ArrayList<Rooms> roomList = new ArrayList<>();
        rAdapter = new RoomViewAdapter(getContext(), roomList);
        final GridView roomGridView = (GridView) getView().findViewById(R.id.room_list);

        @SuppressLint("StaticFieldLeak") HttpGetRequest testTask =new HttpGetRequest(roomsUrl) {
            protected void onPostExecute(String result) {
                if (result!=null) {
                    //   Toast.makeText(DeviceActivity.this, "Response: "+result, Toast.LENGTH_SHORT).show();
                    JSONArray jsonArr = null;
                    JSONObject jsonObj = null;
                    try {
                        jsonArr = new JSONArray(result);
                        String roomName = jsonArr.toString();
                        roomName = roomName.replace("[", "");
                        roomName = roomName.replace("]", "");
                        roomName = roomName.replace("\"", "");
                        rAdapter.add(new Rooms("", roomName));
                        for (int i = 0; i < jsonArr.length(); i++) {
                            jsonObj = jsonArr.getJSONObject(i);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                rAdapter.add(new Rooms("", "Add Room"));
                roomGridView.setAdapter(rAdapter);

            }

        }

                ;
        testTask.execute();


    }
}