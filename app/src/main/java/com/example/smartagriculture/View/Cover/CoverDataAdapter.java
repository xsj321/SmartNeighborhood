package com.example.smartagriculture.View.Cover;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.smartagriculture.Model.Cover.CoverDataListItem;
import com.example.smartagriculture.R;
import java.util.ArrayList;

public class CoverDataAdapter extends RecyclerView.Adapter<CoverDataAdapter.ViewHolder> {
    private ArrayList<CoverDataListItem> list;
    public CoverDataAdapter(ArrayList<CoverDataListItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cover_data_list_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String status = list.get(i).getStatus();
        Log.d("当前名称",status);
        viewHolder.Address.setText(list.get(i).getAddress());
        if (status.equals("正常")){
            viewHolder.Status.setTextColor(Color.parseColor("#20B183"));
        }
        else if (status.equals("已处理")){
            viewHolder.Status.setTextColor(Color.parseColor("#169BD5"));
        }
        else {
            viewHolder.Status.setTextColor(Color.parseColor("#FF0000"));
        }
        viewHolder.Status.setText(status);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView Address;
        private TextView Status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Address = itemView.findViewById(R.id.cover_name);
            Status = itemView.findViewById(R.id.cover_status);
        }
    }
}
