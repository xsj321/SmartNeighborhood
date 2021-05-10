package com.example.smartagriculture.View.Home.DeviceList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartagriculture.R;
import com.example.smartagriculture.Util.DataRequestUtil;
import com.example.smartagriculture.Model.Device.Device;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static java.lang.Thread.sleep;


/**
 * A fragment representing a list of Items.
 */
public class DeviceList extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 0;

    private ArrayList<Device> DevList;

    private DeviceListAdapter deviceListAdapter;
    private             RecyclerView recyclerView;
    private View view;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DeviceList() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static DeviceList newInstance(int columnCount) {
        DeviceList fragment = new DeviceList();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }


        DevList = new ArrayList<>();

        deviceListAdapter = new DeviceListAdapter(DevList, getContext());
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_device_list_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();

            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(deviceListAdapter);

        }

        update(true);

        return view;
    }

    private void update(final boolean now){
        Observable.just("1").map(new Func1<String, JSONObject>() {
            @Override
            public JSONObject call(String s) {
                String body = "{\n" +
                        "\"account\":\"xsj321@outlook.com\"\n" +
                        "}";
                URL url = DataRequestUtil.makeUrl(getContext(), DataRequestUtil.DEVICE_LIST_URL);
                Log.v("请求的数据", body);
                JSONObject requestByPost = DataRequestUtil.requestByPost(url, body);
                if (!now){
                    try {
                        sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                return requestByPost;
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<JSONObject>() {
            @Override
            public void call(JSONObject jsonObject) {
                try {
                    if (jsonObject == null){
                        return;
                    }
                    Log.d("JavaRXTest",jsonObject.toString() );
                    JSONArray data = jsonObject.getJSONArray("data");
                    DevList.clear();
                    for (int i =0;i<data.length() ;i++){
                        DevList.add(new Device(
                                data.getJSONObject(i).getString("id"),
                                data.getJSONObject(i).getString("name"),
                                data.getJSONObject(i).getString("event_id"),
                                data.getJSONObject(i).getJSONObject("components"),
                                data.getJSONObject(i).getBoolean("on_line")
                        ));
                    }
                    deviceListAdapter.freshAdapter();
                    update(false);

                } catch (JSONException e) {
                    e.printStackTrace();
                    update(false);
                }
            }
        });
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        Log.d("设备页面生命周期","onPause()");
//        update();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        Log.d("设备页面生命周期","onResume()");
//        update();
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        Log.d("设备页面生命周期","onAttach()");
//        update();
//    }
}