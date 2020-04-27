package com.example.smartagriculture.Service;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class LoginAndRegisterLoader extends AsyncTaskLoader<String> {

    private String DataIP;
    private String Account;
    private String Passwd;
    private String UserName;
    private boolean doWhat;
    private int DataPort;
    public LoginAndRegisterLoader(@NonNull Context context, String dataIP, int dataPort, String account, String passwd, String userName, boolean doWhat) {
        super(context);
        DataIP = dataIP;
        Account = account;
        Passwd = passwd;
        UserName = userName;
        this.doWhat = doWhat;
        DataPort = dataPort;
    }

    public LoginAndRegisterLoader(@NonNull Context context, String dataIP, int dataPort, String account, String passwd, boolean doWhat) {
        super(context);
        DataIP = dataIP;
        Account = account;
        Passwd = passwd;
        this.doWhat = doWhat;
        DataPort = dataPort;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        try {
            String reply = GetData(DataIP,DataPort);
            return reply;
        }
        catch (Exception e)
        {
            return null;
        }
    }


    private String GetData(String IP, int Port) throws IOException, JSONException, InterruptedException {
        Log.v(IP,String.valueOf(Port));
        Socket socket = new Socket(IP,Port);
        String sendString = SendDataMaker(doWhat);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(sendString.getBytes());
        outputStream.flush();
        sleep(1000);
        InputStream inputStream = socket.getInputStream();
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        byte[] b = new byte[100000];
        String Msg = new String(b, 0, dataInputStream.read(b), "utf8");
        Log.v("收到的数据为",Msg);
        return Msg;
    }

    private String SendDataMaker(boolean Do) throws JSONException {
        if(Do == true)
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("request_type","login");
            jsonObject.put("account_number",Account);
            jsonObject.put("account_password",Passwd);
            String sendString =  jsonObject.toString();
            Log.d("登录生成的字符串",sendString);
            return sendString;
        }
        else
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("request_type","register");
            jsonObject.put("account_username",UserName);
            jsonObject.put("account_number",Account);
            jsonObject.put("account_password",Passwd);
            String snedString = jsonObject.toString();
            return snedString;
        }
    }


}
