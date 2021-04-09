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


/**
 * A fragment representing a list of Items.
 */
public class DeviceList extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 3;

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



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_device_list_list, container, false);

        Observable.just("1").map(new Func1<String, JSONObject>() {
            @Override
            public JSONObject call(String s) {
                String body = "{\n" +
                        "\"account\":\"xsj321@outlook.com\"\n" +
                        "}";
                URL url = DataRequestUtil.makeUrl(getContext(), DataRequestUtil.DEVICE_LIST_URL);
                Log.v("请求的数据", body);
                JSONObject requestByPost = DataRequestUtil.requestByPost(url, body);
                return requestByPost;
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<JSONObject>() {
            @Override
            public void call(JSONObject jsonObject) {
                Log.d("JavaRXTest",jsonObject.toString() );
                try {
                    ArrayList<Device> DevList = new ArrayList<>();
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i =0;i<data.length() ;i++){
                        DevList.add(new Device(
                                data.getJSONObject(i).getString("id"),
                                data.getJSONObject(i).getString("name"),
                                data.getJSONObject(i).getString("event_id"),
                                data.getJSONObject(i).getJSONObject("components")
                                ));
                    }
                    if (view instanceof RecyclerView) {
                        Context context = view.getContext();
                        RecyclerView recyclerView = (RecyclerView) view;
                        if (mColumnCount <= 1) {
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        } else {
                            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                        }
                        recyclerView.setAdapter(new MyItemRecyclerViewAdapter(DevList,getContext()));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        return view;
    }
}