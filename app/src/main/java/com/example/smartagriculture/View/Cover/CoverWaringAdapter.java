package com.example.smartagriculture.View.Cover;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartagriculture.Model.CoverDataListItem;
import com.example.smartagriculture.R;

import java.util.ArrayList;

public class CoverWaringAdapter extends RecyclerView.Adapter<CoverWaringAdapter.ViewHolder> {
    private ArrayList<CoverDataListItem> list;

    public CoverWaringAdapter(ArrayList<CoverDataListItem> list) {
        this.list = list;
        Log.d("创建适配器","waring");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cover_waring_list_item,viewGroup,false);
        Log.d("创建","ViewHolder");
        return new CoverWaringAdapter.ViewHolder(view);
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
        viewHolder.Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView Address;
        private TextView Status;
        private TextView Button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Address = itemView.findViewById(R.id.waring_cover_name);
            Status = itemView.findViewById(R.id.waring_cover_status);
            Button  = itemView.findViewById(R.id.waring_cover_button);
        }
    }
}
