package com.example.smartagriculture.View.Important;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartagriculture.Model.ImportantDataListItem;
import com.example.smartagriculture.R;

import java.util.ArrayList;

public class ImportantDataAdapter extends RecyclerView.Adapter<ImportantDataAdapter.ViewHolder> {

    private ArrayList<ImportantDataListItem> list;

    public ImportantDataAdapter(ArrayList<ImportantDataListItem> list) {
        this.list = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView address;
        private TextView temperature;
        private TextView humidity;
        private TextView air;
        private TextView Status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.important_name);
            temperature = itemView.findViewById(R.id.important_temperature);
            humidity = itemView.findViewById(R.id.important_humidity);
            air = itemView.findViewById(R.id.important_air);
            Status = itemView.findViewById(R.id.important_status);

        }
    }

    @NonNull
    @Override
    public ImportantDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.improtant_data_list_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImportantDataAdapter.ViewHolder viewHolder, int i) {
        ImportantDataListItem nowItem = list.get(i);
        String status = list.get(i).getStatus();
        String airString =  list.get(i).getAir();
        viewHolder.address.setText(nowItem.getAddress());
        viewHolder.temperature.setText(nowItem.getTemperature());
        viewHolder.humidity.setText(nowItem.getHumidity());
        viewHolder.air.setText(nowItem.getAir());
        viewHolder.Status.setText(nowItem.getStatus());
        if (status.equals("正常")){
            viewHolder.Status.setTextColor(Color.parseColor("#20B183"));
        }
        else if (status.equals("已处理")){
            viewHolder.Status.setTextColor(Color.parseColor("#169BD5"));
        }
        else {
            viewHolder.Status.setTextColor(Color.parseColor("#FF0000"));
        }

        if (airString.equals("正常")){
            viewHolder.air.setTextColor(Color.parseColor("#20B183"));
        }
        else if (status.equals("已处理")){
            viewHolder.air.setTextColor(Color.parseColor("#169BD5"));
        }
        else {
            viewHolder.air.setTextColor(Color.parseColor("#FF0000"));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
