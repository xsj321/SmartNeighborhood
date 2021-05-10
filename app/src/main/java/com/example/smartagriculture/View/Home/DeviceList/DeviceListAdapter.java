package com.example.smartagriculture.View.Home.DeviceList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartagriculture.DetailActivity;
import com.example.smartagriculture.R;
import com.example.smartagriculture.dummy.DummyContent.DummyItem;

import com.example.smartagriculture.Model.Device.Device;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.ViewHolder> {

    private final ArrayList<Device> mValues;
    private Context context;

    public DeviceListAdapter(ArrayList<Device> items, Context context) {
        this.context = context;
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_device_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,int position) {
        final Device nowDevice = mValues.get(position);
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(nowDevice.getDeviceId());
        holder.mContentView.setText(nowDevice.getDeviceName());
        Log.d("在线状态！！！！！！！", String.valueOf(nowDevice.isOnline()));

        if (!nowDevice.isOnline()){
            holder.onLine.setBackgroundColor(Color.parseColor("#EE3B3B"));
        }else {
            holder.onLine.setBackgroundColor(Color.parseColor("#039BE5"));
        }


        Log.d("设备组件表：",nowDevice.getDeviceCom().toString());

        holder.itemRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("comData",nowDevice.getDeviceCom().toString());
                context.startActivity(intent);
            }
        });
    }


    public void freshAdapter(){
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Device mItem;
        public LinearLayout itemRoot;
        public LinearLayout onLine;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
            itemRoot = view.findViewById(R.id.item_root);
            onLine = view.findViewById(R.id.is_online);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}