package com.example.invotyx.olacontrols;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class AvailableDevicesAdapter extends ArrayAdapter<Devices> {

    protected Context mContext;
    private List<Devices> deviceList = new ArrayList<>();

    public AvailableDevicesAdapter(@NonNull Context context, ArrayList<Devices> list) {
        super(context, 0 , list);
        mContext = context;
        deviceList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.macros_cell, parent, false);
        }
        Devices currentDevice = deviceList.get(position);

        TextView deviceName = (TextView) listItem.findViewById(R.id.macro_name);
        deviceName.setText(currentDevice.getName());

        ImageView deviceIcon = (ImageView) listItem.findViewById(R.id.macro_icon);
        deviceIcon.setImageResource(currentDevice.getIcon());

        ImageView addIcon = listItem.findViewById(R.id.macros_status);
        addIcon.setImageResource(R.drawable.macro_add);

        return listItem;
    }
}