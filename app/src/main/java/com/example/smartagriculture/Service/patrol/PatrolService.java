package com.example.smartagriculture.Service.patrol;

import android.content.Context;
import android.util.Log;
import com.example.smartagriculture.Model.Patrol.PatrolDataListItem;
import com.example.smartagriculture.Model.Patrol.PatrolPageStatus;
import com.example.smartagriculture.Util.DataRequestUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URL;
import java.util.ArrayList;

public class PatrolService {
   public PatrolPageStatus getPatrolData(Context context, String search_work_name) {
        try{
            String body = "{\n" +
                    "  \"work_name\": \""+search_work_name+"\"\n" +
                    "}";
            Log.e("getPatrolData发送的数据：",body);
            URL url = DataRequestUtil.makeUrl(context, DataRequestUtil.PATROL_LIST_URL);
            JSONObject requestByPost = DataRequestUtil.requestByPost(url, body);

            JSONObject respond = requestByPost.getJSONObject("respond");
            JSONObject detail = respond.getJSONObject("detail");
            Log.d("getPatrolData收到的数据", detail.toString());
            JSONArray JSONdataList = detail.getJSONArray("detail");
            ArrayList<PatrolDataListItem> dataList = new ArrayList<>();
            PatrolPageStatus res;
            if (JSONdataList.length() != 0) {
                for (int i = 0; i < JSONdataList.length(); i++) {
                    JSONObject nowData = JSONdataList.getJSONObject(i);
                    String work_name = nowData.getString("work_name");
                    String date = nowData.getString("dateString");
                    String track = nowData.getString("track");
                    dataList.add(new PatrolDataListItem(
                            work_name,
                            date,
                            track
                    ));
                }
            }
            res = new PatrolPageStatus(dataList);
            return res;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
