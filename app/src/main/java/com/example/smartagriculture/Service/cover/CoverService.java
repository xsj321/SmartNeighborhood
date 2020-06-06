package com.example.smartagriculture.Service.cover;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.smartagriculture.Model.Cover.CoverDataListItem;
import com.example.smartagriculture.Model.Cover.CoverPageStatus;
import com.example.smartagriculture.Service.DataRequestUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class CoverService {
    /**
     * 获取cover页面的信息
     * @param context 上下文
     * @param location 查找地点
     * @return 井盖状态对象
     */
     public CoverPageStatus getCoverData(Context context, @Nullable String location){
        try {
            String body = "{\n" +
                    "  \"place\": \""+location+"\"\n" +
                    "}";
            URL url = DataRequestUtil.makeUrl(context, DataRequestUtil.COVER_LIST_URL);
            JSONObject requestByPost = DataRequestUtil.requestByPost(url, body);
            Log.e("当前登录发送的响应信息", body);
            JSONObject respond = requestByPost.getJSONObject("respond").getJSONObject("cover");
            Log.d("收到的数据",respond.toString());
            if (respond == null) return null;
            JSONArray JSONwaringList = respond.optJSONArray("waring_list");
            JSONArray JSONdataList = respond.getJSONArray("detail");
            ArrayList<CoverDataListItem> waringList = new ArrayList<>();
            ArrayList<CoverDataListItem> dataList = new ArrayList<>();
            CoverPageStatus res;
            //是否警告标记
            Boolean isWaring = false;
            //对警告列表进行判断
            if (JSONwaringList.length() != 0) {
                Log.v("警告列表",String.valueOf(JSONwaringList.length()));
                isWaring = true;
                for (int i = 0; i < JSONwaringList.length(); i++) {
                    JSONObject nowWaring = JSONwaringList.getJSONObject(i);
                    Integer id = nowWaring.getInt("id");
                    String place = nowWaring.getString("place");
                    Boolean waring = nowWaring.getBoolean("waring");
                    //后台懒得改这里做一个转换
                    String waringString = "正常";
                    if (waring) {
                        waringString = "异常";
                    }
                    waringList.add(new CoverDataListItem(id, place, waringString));
                }
            } else {
                waringList = null;
            }

            if (JSONdataList.length() != 0) {
                for (int i = 0;i<JSONdataList.length();i++){
                    JSONObject nowData = JSONdataList.getJSONObject(i);
                    Integer id = nowData.getInt("id");
                    String address = nowData.getString("place");
                    Boolean waring = nowData.getBoolean("waring");
                    String waringString = "正常";
                    if (waring) {
                        waringString = "异常";
                    }
                    dataList.add(new CoverDataListItem(
                            id,
                            address,
                            waringString
                    ));
                }
            }
            CoverPageStatus coverPageStatus = new CoverPageStatus(isWaring, dataList, waringList);

            return coverPageStatus;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
