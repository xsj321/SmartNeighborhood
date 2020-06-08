package com.example.smartagriculture.Service.home;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.smartagriculture.Model.HomePage.HomePageStatus;
import com.example.smartagriculture.Util.DataRequestUtil;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class HomeService {
    public HomeService() {

    }

    /**
     * 获取主页数据
     * @param location 当前位置
     * @param user  用户
     * @return 主页信息
     */
    public HomePageStatus getHomePageData(Context context,@Nullable String location, @Nullable String user){
        try{
            Properties properties  = new Properties();
            try {
                properties.load(context.getAssets().open("server_settings.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String domain = properties.getProperty("request_add");
            URL url = DataRequestUtil.getRequestUrl(domain,null,DataRequestUtil.HOME_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            // 设置连接主机服务器的超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(60000);
            // 发送请求
            connection.connect();
            // 通过connection连接，获取输入流
            int resCode = connection.getResponseCode();
            StringBuilder resString = new StringBuilder();
            if (resCode == HttpURLConnection.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String data;
                while ((data = reader.readLine())!=null){
                    resString.append(data);
                }
            }
            Log.e("首页收到的数据",resString.toString());
            JSONObject respondFromNet = new JSONObject(resString.toString());
            if (respondFromNet == null) return null;
            JSONObject respond = respondFromNet.getJSONObject("respond");
            JSONObject info = respond.getJSONObject("info");
            JSONObject locaion = respond.getJSONObject("locaion");
            JSONObject environment = respond.getJSONObject("environment");
            JSONObject waring = respond.getJSONObject("waring");
            return new HomePageStatus(
                    locaion.getInt("LocationID"),
                    environment.getInt("temperature"),
                    environment.getString("humidity"),
                    environment.getInt("PM"),
                    waring.getBoolean("cover"),
                    waring.getBoolean("important")
            );
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

}
