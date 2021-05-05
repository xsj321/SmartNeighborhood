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
import com.example.smartagriculture.Util.DataRequestUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class DetailActivity extends AppCompatActivity {
    private DeviceDetailAdapter deviceDetailAdapter;

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

        Observable.just("1").map(new Func1<String, JSONObject>() {
            @Override
            public JSONObject call(String s) {
                String body = "{\n" +
                        "\"account\":\"xsj321@outlook.com\"\n" +
                        "}";
                URL url = DataRequestUtil.makeUrl(getBaseContext(), DataRequestUtil.DEVICE_LIST_URL);
                Log.v("请求的数据", body);
                JSONObject requestByPost = DataRequestUtil.requestByPost(url, body);
                return requestByPost;
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<JSONObject>() {
            @Override
            public void call(JSONObject jsonObject) {

                try {
                    Log.d("JavaRXTest",jsonObject.toString() );
                    ArrayList<Component> detailList = new ArrayList<>();
                    JSONArray data = jsonObject.getJSONArray("data");

                    for (int i =0;i<data.length() ;i++){
                        JSONObject components = data.getJSONObject(i).getJSONObject("components");
                        Iterator<String> keys = components.keys();
                        while (keys.hasNext()){
                            String name = keys.next();
                            Log.d("获取到:",name);
                            JSONObject component = components.getJSONObject(name);
                            String type = component.getString("type");
                            if (type.equals("int")){
                                detailList.add(new Component(
                                        component.getString("name"),
                                        component.getString("type"),
                                        component.getInt("value")
                                        ));
                            }
                            if (type.equals("sting") ){
                                detailList.add(new Component(
                                        component.getString("name"),
                                        component.getString("type"),
                                        component.getString("value")
                                ));
                            }
                            if (type.equals("boolean") ){
                                detailList.add(new Component(
                                        component.getString("name"),
                                        component.getString("type"),
                                        component.getBoolean("value")
                                ));
                            }

                        }
                    }

                    deviceDetailAdapter = new DeviceDetailAdapter(detailList, getBaseContext());
                    RecyclerView deviceList  = findViewById(R.id.device_all_info);
                    deviceList.setLayoutManager(new GridLayoutManager(getBaseContext(),2));
                    deviceList.setAdapter(deviceDetailAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}