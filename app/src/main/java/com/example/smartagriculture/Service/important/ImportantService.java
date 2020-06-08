package com.example.smartagriculture.Service.important;

import android.content.Context;
import android.util.Log;

import com.example.smartagriculture.Model.Cover.CoverDataListItem;
import com.example.smartagriculture.Model.Important.ImportantDataListItem;
import com.example.smartagriculture.Model.Important.ImportantPageStatus;
import com.example.smartagriculture.Util.DataRequestUtil;
import com.example.smartagriculture.Util.ResponseVo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class ImportantService {
    private Context context;

    public ImportantService(Context context) {
        this.context = context;
    }

    /**
     * 获取重要地点的列表
     * @param location 地点
     * @return 重要地点重要列表
     */
    public ImportantPageStatus getImportantData( String location) {
        try {
            String body = "{\n" +
                    "  \"place\": \""+location+"\"\n" +
                    "}";
            URL url = DataRequestUtil.makeUrl(context, DataRequestUtil.IMPORTANT_LIST_URL);
            JSONObject requestByPost = DataRequestUtil.requestByPost(url, body);
            JSONObject respond = requestByPost.getJSONObject("respond").getJSONObject("important");
            Log.d("收到的数据", respond.toString());
            if (respond == null) return null;
            JSONArray JSONwaringList = respond.getJSONArray("waring_list");
            JSONArray JSONdataList = respond.getJSONArray("detail");
            ArrayList<CoverDataListItem> waringList = new ArrayList<>();
            ArrayList<ImportantDataListItem> dataList = new ArrayList<>();
            ImportantPageStatus res;
            //是否警告标记
            Boolean isWaring = false;

            //对警告列表进行判断
            if (JSONwaringList.length() != 0) {
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
                for (int i = 0; i < JSONdataList.length(); i++) {
                    JSONObject nowData = JSONdataList.getJSONObject(i);
                    Integer id = nowData.getInt("id");
                    String address = nowData.getString("place");
                    Integer temperature = nowData.getInt("temperature");
                    Integer humidity = nowData.getInt("humidity");
                    Boolean air = nowData.getBoolean("air");
                    Boolean waring = nowData.getBoolean("waring");
                    String waringString = "正常";
                    String airString = "正常";
                    if (waring) {
                        waringString = "异常";
                    }
                    if (air) {
                        airString = "异常";
                    }
                    dataList.add(new ImportantDataListItem(
                            id,
                            address,
                            String.valueOf(temperature),
                            String.valueOf(humidity),
                            airString,
                            waringString
                    ));
                }
            }
            ImportantPageStatus importantPageStatus = new ImportantPageStatus(isWaring, dataList, waringList);
            return importantPageStatus;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 修复重要地点
     * @param id 地点的id
     * @return
     */
    public boolean sendFix(int id){

        String body = "{\n" +
                "  \"id\": "+id+"\n" +
                "}";
        URL url = DataRequestUtil.makeUrl(context, DataRequestUtil.IMPORTANT_FIX_URL);
        Log.v("请求的数据", body);
        JSONObject requestByPost = DataRequestUtil.requestByPost(url, body);
        ResponseVo responseVo = ResponseVo.makeResponseVo(requestByPost);
        return responseVo.isSuccess();
    }
}
