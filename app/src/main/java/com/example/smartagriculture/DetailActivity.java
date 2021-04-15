package com.example.smartagriculture;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.smartagriculture.Model.Device.Component;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("设备详情页");
        Intent intent = getIntent();
        String comData = intent.getStringExtra("comData");
        List<Component> componentList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(comData);
            Iterator<String> comDataKeys = jsonObject.keys();
            while (comDataKeys.hasNext()) {
                String key = comDataKeys.next();
                JSONObject comDataComponent = jsonObject.getJSONObject(key);
                String comName = comDataComponent.getString("name");
                String comType = comDataComponent.getString("type");

                if (comType.equals("int")){
                    componentList.add(new Component(comName,comType,comDataComponent.getInt("value")));
                }
                else {
                    componentList.add(new Component(comName,comType,comDataComponent.getString("value")));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//        Log.d("列表",componentList.get(1).getComponentName());

        List<Component> testList = new ArrayList<>();
        testList.add(new Component("温度","int","22"));
        testList.add(new Component("湿度","int","32"));
        testList.add(new Component("状态","boolean","true"));
        DeviceDetailAdapter deviceDetailAdapter = new DeviceDetailAdapter(testList, this);

        RecyclerView deviceList  = findViewById(R.id.device_all_info);
        deviceList.setLayoutManager(new GridLayoutManager(this,2));
        deviceList.setAdapter(deviceDetailAdapter);

    }
}