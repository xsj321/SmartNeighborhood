package com.example.smartagriculture.View.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartagriculture.View.Home.Controls.ControlFragment;
import com.example.smartagriculture.View.Home.DeviceList.DeviceList;
import com.example.smartagriculture.Model.HomePage.HomePageStatus;
import com.example.smartagriculture.R;
import com.example.smartagriculture.View.Cover.CoverDataActivity;
import com.example.smartagriculture.View.Important.ImportantDataActivity;
import com.example.smartagriculture.View.Patrol.PatrolDataActivity;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {






    private String[] titles = new String[]{"所有设备","警报"};
    private ArrayList<Fragment> fragments = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Intent intent  = getIntent();

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.PureWhite));
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        TabLayout homeTab = findViewById(R.id.home_tab);

        ViewPager viewPager = findViewById(R.id.viewpager);

        homeTab.addTab(homeTab.newTab());
        fragments.add(new DeviceList());
        homeTab.addTab(homeTab.newTab());
        fragments.add(new ControlFragment());
//        homeTab.addTab(homeTab.newTab());
//        fragments.add(new DeviceList());




        homeTab.setupWithViewPager(viewPager,true);
        FmPagerAdapter fmPagerAdapter = new FmPagerAdapter(fragments, getSupportFragmentManager());
        viewPager.setAdapter(fmPagerAdapter);


        for(int i=0 ; i< titles.length;i++){
            homeTab.getTabAt(i).setText(titles[i]);
        }




    }



    @Override
    protected void onResume() {
        super.onResume();
//        supportLoaderManager.restartLoader(1,null,this).forceLoad();
    }
}
