package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.invotyx.olacontrols.R;

import java.util.ArrayList;

public class ChangeIconAdapter extends BaseAdapter {


    private ArrayList<Integer> icon_ref = new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;

    public ChangeIconAdapter(Context con, ArrayList<Integer> icon_ref)
    {
        this.context = con;
        this.layoutInflater = LayoutInflater.from(con);
        this.icon_ref = icon_ref;
    }

    public ChangeIconAdapter(Context con)
    {
        this.context = con;
        this.layoutInflater = LayoutInflater.from(con);
    }


    @Override
    public int getCount() {
        return icon_ref.size();
    }

    @Override
    public Object getItem(int position) {
        return icon_ref.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView = layoutInflater.inflate(R.layout.change_icon_cell,parent,false);
            ImageView icon = convertView.findViewById(R.id.change_icon_resource);
            icon.setImageResource(icon_ref.get(position));
            icon.setImageAlpha(convertView.getResources().getColor(R.color.bg_grey));

        }
        return convertView;
    }

    public int getItemResId(int position)
    {
        return icon_ref.get(position);
    }

    public void fetchAllIcons()
    {
        fetchDeviceIcons();
        fetchMacroIcons();
        fetchRoomIcons();
        fetchSceneIcons();
    }

    public void fetchRoomIcons()
    {
        icon_ref.add(R.drawable.mainscreen_bathroom);
        icon_ref.add(R.drawable.mainscreen_kitchen);
        icon_ref.add(R.drawable.mainscreen_livingroom);
    }
    public void fetchDeviceIcons()
    {
        icon_ref.add(R.drawable.mainscreen_illumination);
        icon_ref.add(R.drawable.mainscreen_camera);
        icon_ref.add(R.drawable.mainscreen_door);
        icon_ref.add(R.drawable.mainscreen_humidity);
        icon_ref.add(R.drawable.mainscreen_siren);
        icon_ref.add(R.drawable.mainscreen_smokesensor);
        icon_ref.add(R.drawable.mainscreen_temperature);
        icon_ref.add(R.drawable.mainscreen_pir);
    }
    public void fetchMacroIcons()
    {
        icon_ref.add(R.drawable.macro_pir);
    }
    public void fetchSceneIcons()
    {

    }
}
