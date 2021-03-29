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

import com.example.smartagriculture.View.Home.DeviceList.DeviceList;
import com.example.smartagriculture.Model.HomePage.HomePageStatus;
import com.example.smartagriculture.R;
import com.example.smartagriculture.View.Cover.CoverDataActivity;
import com.example.smartagriculture.View.Important.ImportantDataActivity;
import com.example.smartagriculture.View.Patrol.PatrolDataActivity;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity implements LoaderManager.LoaderCallbacks<HomePageStatus> {

    private FrameLayout PatrolButton,CoverButton,ImportantButton;
    private TextView Temp,Him,PM,NowLocation;
    private ImageView CoverIMG,ImportantIMG;
    private String NowUserName;
    private LoaderManager supportLoaderManager;
    private String[] titles = new String[]{"所有设备","警报","任务"};
    private ArrayList<Fragment> fragments = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Intent intent  = getIntent();
        this.NowUserName = intent.getStringExtra("user_name");
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.PureWhite));
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        PatrolButton = findViewById(R.id.patrol_button);
        CoverButton = findViewById(R.id.cover_button);
        ImportantButton =  findViewById(R.id.important_button);
        Temp = findViewById(R.id.body_temp);
        Him = findViewById(R.id.now_him);
        PM = findViewById(R.id.now_pm);
        CoverIMG = findViewById(R.id.cover_waring);
        ImportantIMG = findViewById(R.id.important_waring);
        NowLocation = findViewById(R.id.now_location);
        TabLayout homeTab = findViewById(R.id.home_tab);

        ViewPager viewPager = findViewById(R.id.viewpager);
        for(int i=0 ; i< titles.length;i++){
            homeTab.addTab(homeTab.newTab());
            fragments.add(new DeviceList());
        }



        homeTab.setupWithViewPager(viewPager,true);
        FmPagerAdapter fmPagerAdapter = new FmPagerAdapter(fragments, getSupportFragmentManager());
        viewPager.setAdapter(fmPagerAdapter);


        for(int i=0 ; i< titles.length;i++){
            homeTab.getTabAt(i).setText(titles[i]);
        }


        PatrolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), PatrolDataActivity.class);
                intent.putExtra("user_name",NowUserName);
                startActivity(intent);
            }
        });

        CoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CoverDataActivity.class);
                intent.putExtra("user_name",NowUserName);
                startActivity(intent);
            }
        });
        ImportantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ImportantDataActivity.class);
                intent.putExtra("user_name",NowUserName);
                startActivity(intent);
            }
        });
//        supportLoaderManager = getSupportLoaderManager();
//        supportLoaderManager.initLoader(1,null,this).forceLoad();
    }

    @NonNull
    @Override
    public Loader<HomePageStatus> onCreateLoader(int i, @Nullable Bundle bundle) {
        // TODO: 2020/4/21 0021 记得修改占位符
        return new HomePageLoader(this,"四川省成都市锦江区东光街道",NowUserName);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<HomePageStatus> loader, HomePageStatus homePageStatus) {
        if (homePageStatus  == null){
            return;
        }
        Temp.setText(String.valueOf(homePageStatus.getTemperature()));
        Him.setText(String.valueOf(homePageStatus.getHumidity()));
        PM.setText(String.valueOf(homePageStatus.getPm2_5()));
        NowLocation.setText(homePageStatus.getNowPlace());
        if (homePageStatus.getCover())CoverIMG.setVisibility(View.VISIBLE);
        else CoverIMG.setVisibility(View.GONE);
        if (homePageStatus.getImportant())ImportantIMG.setVisibility(View.VISIBLE);
        else ImportantIMG.setVisibility(View.GONE);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<HomePageStatus> loader) {

    }

    @Override
    protected void onResume() {
        super.onResume();
//        supportLoaderManager.restartLoader(1,null,this).forceLoad();
    }
}
