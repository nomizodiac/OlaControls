package com.example.invotyx.olacontrols;

import android.annotation.SuppressLint;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetDeviceFunc {

    private Devices deviceSet[];
    private Boolean isExecuted=false;
    String result ="";





    public Devices[] getDev() {

        IP ip = new IP();
        String devicesUrl = ip.getIP()+"/network.cgi";
        String devicesBody = "json="+"{\"control\":{\"cmd\":\"getdevice\",\"uid\":256}}";

        POST getDevicesTask =new POST(devicesUrl, devicesBody);

        String result = getDevicesTask.getResult();
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
                    deviceSet = new Devices[SIZE];
                    for(int h=0;h<SIZE;h++)
                    {
                        deviceSet[h]=tempDev[h];

                    }


                    Log.i("AllDEV SIZE",""+deviceSet.length);

                } catch (JSONException e) {
                    e.printStackTrace();

                }





        return deviceSet;
    }
}
