package com.example.smartagriculture.Service;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.smartagriculture.Model.Cover.CoverPageStatus;
import com.example.smartagriculture.Model.Important.ImportantPageStatus;

import org.json.JSONException;

import java.io.IOException;
import java.util.Properties;

public class CoverPageLoader extends AsyncTaskLoader<CoverPageStatus> {
    private String  IP;
    private int Port;
    private String location;
    private String user;
    public CoverPageLoader(@NonNull Context context, String location, String user) {
        super(context);
        this.location = location;
        this.user = user;
        Properties properties  = new Properties();
        try {
            properties.load(context.getAssets().open("server_settings.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        IP = properties.getProperty("server_ip");
        Port  = Integer.parseInt(properties.getProperty("server_port"));
        this.location = location;
        this.user = user;
    }

    @Nullable
    @Override
    public CoverPageStatus loadInBackground() {
        DataRequestUtil  util = DataRequestUtil.getInstance(IP,Port);
        CoverPageStatus coverPageStatus = null;
        try {
            coverPageStatus = util.getCoverData(location,user);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return coverPageStatus;
    }

}
