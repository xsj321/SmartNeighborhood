package com.example.smartagriculture.Util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.smartagriculture.Model.Patrol.PatrolDataListItem;
import com.example.smartagriculture.Model.Patrol.PatrolPageStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

public class DataRequestUtil {
    private String IP;
    private int Port;
    private String mainAddress;
    public static final String LOGIN_URL = "loginController/login";
    public static final String REGISTER_URL = "loginController/register";
    public static final String HOME_URL = "homeController/home";
    public static final String COVER_LIST_URL = "coverController/cover";
    public static final String COVER_FIX_URL = "coverController/fixCover";
    public static final String IMPORTANT_LIST_URL =  "importantController/getImportant";
    public static final String PATROL_LIST_URL = "patrolController/getImportant";



    public DataRequestUtil(String IP, int port) {
        this.IP = IP;
        Port = port;
    }

    public DataRequestUtil(String address) {
        mainAddress = address;
    }

    static DataRequestUtil getInstance(String IP, int port) {
        return new DataRequestUtil(IP, port);
    }

    static DataRequestUtil getInstance(String address) {
        return new DataRequestUtil(address);
    }


    PatrolPageStatus getPatrolData(String location, String user) throws IOException, JSONException {
        JSONObject respond = request("patrol_page", user, location).getJSONObject("detail");
        Log.d("getPatrolData收到的数据", respond.toString());
        JSONArray JSONdataList = respond.getJSONArray("detail");
        ArrayList<PatrolDataListItem> dataList = new ArrayList<>();
        PatrolPageStatus res;
        if (JSONdataList.length() != 0) {
            for (int i = 0; i < JSONdataList.length(); i++) {
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

        Log.v("请求的数据", data);
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
        Log.v("返回的数据", Msg);
        inputStream.close();
        outputStream.close();
        dataInputStream.close();
        socket.close();
        return new JSONObject(Msg);
    }

    public void sendFix(String type, String user, String location, String locationID) throws IOException {

        String data = "{\n" +
                "    \"request\": {\n" +
                "        \"request_type\": \"" + type + "\",\n" +
                "        \"request_user\": \"" + user + "\",\n" +
                "        \"request_Location\": \"" + locationID + "\"\n" +
                "    },\n" +
                "    \"fix\": {\n" +
                "        \"id\": " + locationID + ",\n" +
                "        \"place\": \"" + location + "\"\n" +
                "    }\n" +
                "}\n";
        Log.v("请求的数据", data);
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
     *
     * @param mainAddress 服务器域名
     * @param port        请求端口
     * @param subAddress  详细路径
     * @return 请求的URL
     */
    public static URL getRequestUrl(String mainAddress, @Nullable Integer port, String subAddress) {
        String urlStr = mainAddress + "/" + subAddress;
        Log.e("url为", urlStr);
        try {
            URL url = new URL(urlStr);
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("url无效", urlStr);
            return null;
        }
    }

    /***
     * 创建返回数据对象
     * @param str 返回信息JSON字符串
     * @return ResponseVo对象
     */
    public static ResponseVo buildResponseVoByJson(String str) {
        ResponseVo res = new ResponseVo();
        if (str != null && str != "") {
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


    /**
     * 通过POST发送数据
     *
     * @param url  url
     * @param body 请求JSON
     * @return 响应JSON
     */
    public static JSONObject requestByPost(URL url, String body) {
        Log.e("当前登录发送的响应信息", body);
        HttpURLConnection connection;
        StringBuilder resString = new StringBuilder();
        JSONObject res = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestProperty("content-type", "application/json");
            connection.setRequestProperty("connection", "keep-alive");
            connection.setRequestProperty("keep-alive", "timeout=60");
            connection.connect();
            OutputStream outputStream = connection.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeBytes(body);
            Log.e("写入完成", "!!!!!");
            dataOutputStream.close();
            int resCode = connection.getResponseCode();
            Log.e("响应代码", String.valueOf(resCode));
            if (resCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String data;
                while ((data = reader.readLine()) != null) {
                    resString.append(data);
                }
            }
            Log.d("收到的响应信息", resString.toString());
            res = new JSONObject(resString.toString());
            if (res == null) {
                return res;
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static URL makeUrl(Context context, String subPath) {
        try {
            Properties properties = new Properties();
            try {
                properties.load(context.getAssets().open("server_settings.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String domain = properties.getProperty("request_add");
            URL url = DataRequestUtil.getRequestUrl(domain, null, subPath);
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}