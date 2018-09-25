package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.invotyx.olacontrols.R;

import java.util.ArrayList;

import dataclasses.Event;

public class EditDeviceEventAdapter extends BaseAdapter {


    private ArrayList<Event> events;
    private Context context;
    private LayoutInflater layoutInflater;

    public EditDeviceEventAdapter(Context con, ArrayList<Event> events)
    {
        this.context = con;
        this.layoutInflater = LayoutInflater.from(con);
        this.events = events;
    }


    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView = layoutInflater.inflate(R.layout.edit_device_event_cell,parent,false);
            TextView eventTime = convertView.findViewById(R.id.event_cell_time);
            TextView eventMsg = convertView.findViewById(R.id.event_cell_msg);

            eventTime.setText(events.get(position).getTime());
            eventMsg.setText(events.get(position).getMsg());
        }
        return convertView;
    }
}
