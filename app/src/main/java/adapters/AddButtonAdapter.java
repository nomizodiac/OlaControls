package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.invotyx.olacontrols.R;

public class AddButtonAdapter extends BaseAdapter {

    int add_btn;
    Context context;
    LayoutInflater layoutInflater;


    public AddButtonAdapter(Context con)
    {
        this.context = con;
        this.layoutInflater = LayoutInflater.from(con);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return 0;
    }
    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.add_cell_rounded,parent,false);
        }
        return convertView;
    }


}
