package com.example.invotyx.olacontrols;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EditDeviceRoomListAdapter extends ArrayAdapter<Rooms> {

private Context mContext;
private List<Rooms> roomList = new ArrayList<>();

public EditDeviceRoomListAdapter(@NonNull Context context, ArrayList<Rooms> list) {
        super(context, 0 , list);
        mContext = context;
        roomList = list;
        }

@NonNull
@Override
public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
        listItem = LayoutInflater.from(mContext).inflate(R.layout.edit_device_room_list_cell,parent,false);

        Rooms currentRoom = roomList.get(position);

    RadioButton room = convertView.findViewById(R.id.list_room_title);
    room.setText(currentRoom.getName());

        return listItem;
        }
        }