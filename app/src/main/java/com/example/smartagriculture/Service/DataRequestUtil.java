package com.example.smartagriculture.Service;

import android.net.Uri;
import android.util.Log;

import com.example.smartagriculture.Model.Cover.CoverDataListItem;
import com.example.smartagriculture.Model.Cover.CoverPageStatus;
import com.example.smartagriculture.Model.Important.ImportantDataListItem;
import com.example.smartagriculture.Model.HomePage.HomePageStatus;
import com.example.smartagriculture.Model.Important.ImportantPageStatus;
import com.example.smartagriculture.Model.Patrol.PatrolDataListItem;
import com.example.smartagriculture.Model.Patrol.PatrolPageStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

public class DataRequestUtil {
    private String IP;
    private int Port;
    public static final String LOGIN_URL = "loginController/login";
    public static final String REGISTER_URL = "loginController/register";

    public DataRequestUtil(String IP, int port) {
        this.IP = IP;
        Port = port;
    }

    static DataRequestUtil getInstance(String IP, int port) {
        return new DataRequestUtil(IP, port);
    }

    /**
     * 向服务器请求主页的数据
     *
     * @param location
     * @param user
     * @return
     * @throws IOException
     * @throws JSONException
     */
    HomePageStatus getHomePageData(String location, String user) throws IOException, JSONException {
        JSONObject respondFromNet = request("home_page", user, location);
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
    }

    /**
     * 获取Important页面的信息
     * @param location
     * @param user
     * @return
     * @throws IOException
     * @throws JSONException
     */
    ImportantPageStatus getImportantData(String location, String user) throws IOException, JSONException {
        JSONObject respond = request("important_page", user, location).getJSONObject("respond").getJSONObject("important");
        Log.d("收到的数据",respond.toString());
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
            for (int i = 0;i<JSONdataList.length();i++){
                JSONObject nowData = JSONdataList.getJSONObject(i);
                Integer id = nowData.getInt("id");
                String address = nowData.getString("place");
                Integer temperature = nowData.getInt("temperature");
                Integer humidity = nowData.getInt("humidity");
                Boolean air = nowData.getBoolean("air");
                Boolean waring = nowData.getBoolean("waring");
                String waringString = "正常";
                String airString = "异常";
                if (waring) {
                    waringString = "异常";
                }
                if (air){
                    airString = "正常";
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
    }

    /**
     * 获取cover页面的信息
     * @param location
     * @param user
     * @return
     * @throws IOException
     * @throws JSONException
     */
    CoverPageStatus getCoverData(String location, String user) throws IOException, JSONException {
        JSONObject respond = request("cover_page", user, location).getJSONObject("respond").getJSONObject("cover");
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
    }


    PatrolPageStatus getPatrolData(String location, String user) throws IOException, JSONException {
        JSONObject respond = request("patrol_page", user, location).getJSONObject("detail");
        Log.d("getPatrolData收到的数据",respond.toString());
        JSONArray JSONdataList = respond.getJSONArray("detail");
        ArrayList<PatrolDataListItem> dataList = new ArrayList<>();
        PatrolPageStatus res;
        if (JSONdataList.length() != 0) {
            for (int i = 0;i<JSONdataList.length();i++){
                JSONObject nowData = JSONdataList.getJSONObject(i);
                String work_name = nowData.getString("work_name");
                String date = nowData.getString("date");
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
    }

    private JSONObject request(String type, String user, String location) throws IOException, JSONException {
        String data = "{\n" +
                "    \"request\": {\n" +
                "        \"request_type\": \"" + type + "\",\n" +
                "        \"request_user\": \"" + user + "\",\n" +
                "        \"request_Location\": \"" + location + "\"\n" +
                "    }\n" +
                "}\n";

        Log.v("请求的数据",data);
        Socket socket = new Socket(IP, Port);
//        Socket socket = new Socket("127.0.0.1",8888);
        OutputStream outputStream = socket.getOutputStream();
        String header = "app\n";
        byte[] ob = header.getBytes();
        outputStream.write(ob);
        outputStream.write(data.getBytes("GBK"));
        InputStream inputStream = socket.getInputStream();
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        byte[] b = new byte[100000];
        int length = dataInputStream.read(b);
        if (length == -1) {
            return null;
        }
        String Msg = new String(b, 0, length, "GBK");
        Log.v("返回的数据",Msg);
        inputStream.close();
        outputStream.close();
        dataInputStream.close();
        socket.close();
        return new JSONObject(Msg);
    }

    public void sendFix(String type, String user, String location,String locationID) throws IOException {

        String data = "{\n" +
                "    \"request\": {\n" +
                "        \"request_type\": \""+type+"\",\n" +
                "        \"request_user\": \""+user+"\",\n" +
                "        \"request_Location\": \""+locationID+"\"\n" +
                "    },\n" +
                "    \"fix\": {\n" +
                "        \"id\": "+locationID+",\n" +
                "        \"place\": \""+location+"\"\n" +
                "    }\n" +
                "}\n";
        Log.v("请求的数据",data);
        Socket socket = new Socket(IP, Port);
//        Socket socket = new Socket("127.0.0.1",8888);
        OutputStream outputStream = socket.getOutputStream();
        String header = "app\n";
        byte[] ob = header.getBytes();
        outputStream.write(ob);
        outputStream.write(data.getBytes("GBK"));
        outputStream.close();
        socket.close();
    }

    /**
     * 获取请求的URL
     * @param mainAddress 服务器域名
     * @param port 请求端口
     * @param subAddress 详细路径
     * @return 请求的URL
     */
    public static URL getRequestUri(String mainAddress,int port,String subAddress){
        String urlStr = mainAddress+"/"+subAddress;
        Log.e("url为",urlStr);
            try {
                URL url = new URL(urlStr);
                return url;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e("url无效",urlStr);
                return null;
            }
    }


    public static ResponseVo buildResponseVoByJson(String str){
        ResponseVo res = new ResponseVo();
        if (str!=null&&str!=""){
            try {
                JSONObject resJson = new JSONObject(str);
                res.setCode(resJson.getInt("code"));
                res.setMsg(resJson.getString("msg"));
                res.setSuccess(resJson.getBoolean("success"));
                return res;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
