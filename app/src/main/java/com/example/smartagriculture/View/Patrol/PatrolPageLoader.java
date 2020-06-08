package com.example.smartagriculture.View.Patrol;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.smartagriculture.Model.Patrol.PatrolPageStatus;
import com.example.smartagriculture.Service.patrol.PatrolService;
import org.json.JSONException;
import java.io.IOException;
import java.util.Properties;

public class PatrolPageLoader extends AsyncTaskLoader<PatrolPageStatus> {

    private String  IP;
    private int Port;
    private String location;
    private String user;
    private PatrolService service;
    private String workName;
    private Context context;

    public PatrolPageLoader(@NonNull Context context, String workName) {
        super(context);
        this.workName = workName;
        this.user = user;
        this.context = context;
        this.service = new PatrolService();
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
    public PatrolPageStatus loadInBackground() {
        return service.getPatrolData(context, workName);
    }
}
