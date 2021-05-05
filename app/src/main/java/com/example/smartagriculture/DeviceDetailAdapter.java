package com.example.smartagriculture;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartagriculture.Model.Device.Component;

import java.util.List;

public class DeviceDetailAdapter extends RecyclerView.Adapter<DeviceDetailAdapter.ViewHolder> {


    private Context mContext;
    private List<Component> componentList;
    public DeviceDetailAdapter(List<Component> componentList, Context context) {
        mContext = context;
        this.componentList = componentList;
    }

    @NonNull
    @Override
    public DeviceDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.device_detail_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceDetailAdapter.ViewHolder viewHolder, int i) {
        String componentName = componentList.get(i).getComponentName();
        String value = componentList.get(i).getValue();
        Log.d("当前组件名称",componentName);
        Log.d("当前组件数值",value);

        viewHolder.valueName.setText(componentName);
        viewHolder.valueValue.setText(value);
    }

    @Override
    public int getItemCount() {
        return componentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView valueName;
        public TextView valueValue;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            valueName = itemView.findViewById(R.id.value_name);
            valueValue = itemView.findViewById(R.id.value_value);
        }
    }
}
