package com.example.smartagriculture;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartagriculture.View.Home.DeviceList.MyItemRecyclerViewAdapter;

public class DeviceDetailAdapter extends RecyclerView.Adapter<DeviceDetailAdapter.ViewHolder> {
    @NonNull
    @Override
    public DeviceDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_device_list, viewGroup, false);
        return new DeviceDetailAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceDetailAdapter.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
