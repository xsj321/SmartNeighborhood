package com.example.smartagriculture.Service.login;

import android.util.Log;

import com.example.smartagriculture.Service.DataRequestUtil;
import com.example.smartagriculture.Service.ErrorCode;
import com.example.smartagriculture.Service.ResponseVo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginService {
    public LoginService() {

    }

    public ResponseVo requestByPost(URL url, String accountNumber, String accountPassword){
        String body = "{\n" +
                "  \"accountNumber\": \""+accountNumber+"\",\n" +
                "  \"accountPassword\": \""+accountPassword+"\",\n" +
                "  \"id\": 0\n" +
                "}";
        Log.e("当前登录发送的响应信息", body);
        HttpURLConnection connection = null;
        StringBuilder resString = new StringBuilder();
        ResponseVo res = ResponseVo.buildFailInstance(ErrorCode.FAIL,"登录失败");
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
            Log.e("写入完成","!!!!!");
            dataOutputStream.close();
            int resCode = connection.getResponseCode();
            Log.e("响应代码", String.valueOf(resCode));
            if (resCode == HttpURLConnection.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String data;
                while ((data = reader.readLine())!=null){
                    resString.append(data);
                }
            }
            Log.d("登录收到的响应信息",resString.toString());
            ResponseVo responseVo = DataRequestUtil.buildResponseVoByJson(resString.toString());
            if (responseVo == null){
                return res;
            }
            res = responseVo;
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
