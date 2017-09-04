package com.example.bradleychippi.water_test_kit;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * Created by waqas on 9/1/2017.
 */

public class ListItemAdapter extends BaseAdapter {

    ArrayList info;
    Context context;
    LayoutInflater cellInflater;
    ListModel temp = null;

    //When called pass in Location, Data, DateTime converted to string, Context
    public ListItemAdapter(ArrayList a, Context context){
        info = a;
        this.context = context;

        cellInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        //Log.e(TAG, "getCount: " + data.length);
        return info.size();
    }

    @Override
    public Object getItem(int position) {
        return info.get(position);//+ " " + locations[position] + " " + dates[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder{
        public TextView datum;
        public TextView day;
        public TextView place;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;


        if(convertView == null){
            vi = cellInflater.inflate(R.layout.item_table_row,parent,false);
            holder = new ViewHolder();
            holder.datum = (TextView) vi.findViewById(R.id.ColumnData);
            holder.day = (TextView) vi.findViewById(R.id.ColumnDateTime);
            holder.place = (TextView) vi.findViewById(R.id.ColumnLocation);

            vi.setTag(holder);
            //convertView = cellInflater.inflate(R.layout.item_table_row,parent,false);
            //convertView = cellInflater.inflate(android.R.layout.simple_list_item_1,parent,false);
        }else{
            holder = (ViewHolder) vi.getTag();
        }
        if(info.size()<=0){
            holder.datum.setText("No Data");

        }else{
            temp = null;
            temp = (ListModel) info.get(position);
            Log.e(TAG, "getPosition: " + temp.getData());
            holder.datum.setText(temp.getData());
            holder.day.setText(temp.getDateTime());
            holder.place.setText(temp.getLocation());
        }
//        TextView loc = (TextView) convertView.findViewById(R.id.ColumnLocation);
//        TextView dateTime = (TextView) convertView.findViewById(R.id.ColumnDateTime);
//        TextView info = (TextView) convertView.findViewById(R.id.ColumnData);
//        Log.e(TAG, "getPosition: " + position);
//        loc.setText(locations[position]);
//        dateTime.setText(dates[position]);
//        info.setText(data[position]);

        return vi;
    }
}
