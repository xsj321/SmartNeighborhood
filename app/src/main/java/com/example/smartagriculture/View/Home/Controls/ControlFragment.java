package com.example.smartagriculture.View.Home.Controls;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartagriculture.Model.HomePage.HomePageStatus;
import com.example.smartagriculture.R;
import com.example.smartagriculture.View.Cover.CoverDataActivity;
import com.example.smartagriculture.View.Home.HomePageLoader;
import com.example.smartagriculture.View.Important.ImportantDataActivity;
import com.example.smartagriculture.View.Patrol.PatrolDataActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ControlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ControlFragment extends Fragment implements LoaderManager.LoaderCallbacks<HomePageStatus>  {


    private FrameLayout PatrolButton,CoverButton,ImportantButton;
    private TextView Temp,Him,PM,NowLocation;
    private ImageView CoverIMG,ImportantIMG;

    private LoaderManager supportLoaderManager;

    private String NowUserName;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "control-count";
    // TODO: Customize parameters
    private int mColumnCount = 3;

    private View view;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ControlFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ControlFragment newInstance(int columnCount) {
        ControlFragment fragment = new ControlFragment();
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

        view = inflater.inflate(R.layout.fragment_control, container, false);


        PatrolButton = view.findViewById(R.id.patrol_button);
        CoverButton = view.findViewById(R.id.cover_button);
        ImportantButton =  view.findViewById(R.id.important_button);
        CoverIMG = view.findViewById(R.id.cover_waring);
        ImportantIMG = view.findViewById(R.id.important_waring);

        Temp = view.findViewById(R.id.body_temp);
        Him = view.findViewById(R.id.now_him);
        PM = view.findViewById(R.id.now_pm);

        NowLocation = view.findViewById(R.id.now_location);


        this.NowUserName = getActivity().getIntent().getStringExtra("user_name");

        PatrolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PatrolDataActivity.class);
                intent.putExtra("user_name",NowUserName);
                startActivity(intent);
            }
        });

        CoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CoverDataActivity.class);
                intent.putExtra("user_name",NowUserName);
                startActivity(intent);
            }
        });
        ImportantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ImportantDataActivity.class);
                intent.putExtra("user_name",NowUserName);
                startActivity(intent);
            }
        });

        //        supportLoaderManager = getSupportLoaderManager();
        //        supportLoaderManager.initLoader(1,null,this).forceLoad();

        return view;
    }

    @NonNull
    @Override
    public Loader<HomePageStatus> onCreateLoader(int i, @Nullable Bundle bundle) {
        // TODO: 2020/4/21 0021 记得修改占位符
        return new HomePageLoader(getContext(),"四川省成都市锦江区东光街道",NowUserName);
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
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        Log.d("控制页面生命周期","onPause()");
//
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        Log.d("控制页面生命周期","onResume()");
//
//    }


}