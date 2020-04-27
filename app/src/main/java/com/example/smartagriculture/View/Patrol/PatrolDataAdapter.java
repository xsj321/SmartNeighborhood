package com.example.smartagriculture.View.Patrol;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartagriculture.Model.PatrolDataListItem;
import com.example.smartagriculture.R;

import java.util.ArrayList;

public class PatrolDataAdapter extends RecyclerView.Adapter<PatrolDataAdapter.ViewHolder>{
    private ArrayList<PatrolDataListItem> list;

    public PatrolDataAdapter(ArrayList<PatrolDataListItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.patrol_data_list_item,viewGroup,false);
        ViewHolder viewHolder =  new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.workName.setText(list.get(i).getWorkerName());
        viewHolder.data.setText(list.get(i).getDate());
        viewHolder.way.setText(list.get(i).getWay());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView workName;
        TextView data;
        TextView way;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            workName = itemView.findViewById(R.id.worker_name);
            data = itemView.findViewById(R.id.date);
            way = itemView.findViewById(R.id.way);

        }
    }
}
