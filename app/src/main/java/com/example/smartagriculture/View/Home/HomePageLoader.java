package com.example.smartagriculture.View.Home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;

import com.example.smartagriculture.Model.HomePage.HomePageStatus;
import com.example.smartagriculture.Service.home.HomeService;

public class HomePageLoader extends AsyncTaskLoader<HomePageStatus> {
    private String location;
    private String user;
    private Context context;
    private HomeService service;
    public HomePageLoader(@NonNull Context context,String location,String user){
        super(context);
        this.context =context;
        this.service = new HomeService();
        this.location = location;
        this.user = user;
    }

    @Override
    public HomePageStatus loadInBackground() {
        HomePageStatus homePageData = service.getHomePageData(context,location, user);
        return homePageData;
    }
}
