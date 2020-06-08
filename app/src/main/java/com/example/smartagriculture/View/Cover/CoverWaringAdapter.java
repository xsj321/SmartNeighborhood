package com.example.smartagriculture.View.Cover;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartagriculture.Model.Cover.CoverDataListItem;
import com.example.smartagriculture.Model.Cover.CoverPageStatus;
import com.example.smartagriculture.R;
import com.example.smartagriculture.Service.cover.CoverService;
import com.example.smartagriculture.View.CustomViews.DataTable;

import java.util.ArrayList;

public class CoverWaringAdapter extends RecyclerView.Adapter<CoverWaringAdapter.ViewHolder> {
    private ArrayList<CoverDataListItem> list;
    private Activity activity;
    private CoverService coverService;
    public CoverWaringAdapter(ArrayList<CoverDataListItem> list, Activity activity) {
        this.list = list;
        this.activity = activity;
        coverService = new CoverService(activity);
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
        CoverDataListItem nowItem = list.get(i);
        final int id = nowItem.getId();
        final String place = nowItem.getAddress();
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
            @SuppressLint("HandlerLeak")
            Handler handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what){
                        case 1:
                            CoverDataActivity.coverWaringAdapter.notifyDataSetChanged();
                            CoverDataActivity.coverDataAdapter.notifyDataSetChanged();
                            Log.e("收到刷新消息", String.valueOf(msg.what));
                            break;
                        case 2:
                            DataTable rootLayout =  activity.findViewById(R.id.cover_root);
                            rootLayout.setWarningBoxVisibility(false);
                            CoverDataActivity.coverDataAdapter.notifyDataSetChanged();
                    }
                }
            };
            @Override
            public void onClick(View v) {

                Runnable networkTask = new Runnable() {
                    @Override
                    public void run() {
                        coverService.sendFix(id);
                        CoverPageStatus nowCoverData = coverService.getCoverData(null);
                        CoverDataActivity.list_data.clear();
                        CoverDataActivity.list_data.addAll(nowCoverData.getDataList());
                        if (nowCoverData.getWaringList() != null){
                            CoverDataActivity.list_waring.clear();
                            CoverDataActivity.list_waring.addAll(nowCoverData.getWaringList());
                            handler.sendEmptyMessage(1);
                        }
                        else {
                            handler.sendEmptyMessage(2);
                        }


                    }
                };
                new Thread(networkTask).start();
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
