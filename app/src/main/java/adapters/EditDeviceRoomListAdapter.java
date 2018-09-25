package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import com.example.invotyx.olacontrols.R;

import java.util.ArrayList;

import dataclasses.Rooms;

public class EditDeviceRoomListAdapter extends BaseAdapter {


    private ArrayList<Rooms> rooms;
    private Context context;
    private LayoutInflater layoutInflater;

    public EditDeviceRoomListAdapter(Context con, ArrayList<Rooms> rooms)
    {
        this.context = con;
        this.layoutInflater = LayoutInflater.from(con);
        this.rooms= rooms;
    }


    @Override
    public int getCount() {
        return rooms.size();
    }

    @Override
    public Object getItem(int position) {
        return rooms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView = layoutInflater.inflate(R.layout.edit_device_room_list_cell,parent,false);

            RadioButton room = convertView.findViewById(R.id.list_room_title);

            room.setText(rooms.get(position).getName());
        }
        return convertView;
    }
}
