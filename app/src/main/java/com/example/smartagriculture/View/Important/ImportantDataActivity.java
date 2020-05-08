package com.example.smartagriculture.View.Important;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.smartagriculture.View.CustomViews.DataTable;
import com.example.smartagriculture.Service.ImportantPageLoader;
import com.example.smartagriculture.R;
import com.example.smartagriculture.Model.Important.ImportantPageStatus;

public class ImportantDataActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ImportantPageStatus> {
    private SearchView mSearchView;
    private DataTable rootLayout;
    private String NowUserName;

    //    public static final ArrayList<ImportantDataListItem> dataList =  new ArrayList<>();
//    public static final ArrayList<CoverDataListItem> list_waring = new ArrayList<CoverDataListItem>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_important_data);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.PureWhite));
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        Intent  intent = getIntent();
        this.NowUserName = intent.getStringExtra("user_name");
        rootLayout = findViewById(R.id.important_root);
        rootLayout.setPageTitle("重要场所");
        rootLayout.getDataTitleView().setText(getResources().getText(R.string.important_data_title));
        rootLayout.setWarningBoxVisibility(false);
        rootLayout.setWarningTitle(getResources().getString(R.string.important_data_waring_title));
        Toolbar toolbar = rootLayout.getToolbar();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setTitle(null);
        getSupportLoaderManager().initLoader(1,null,this).forceLoad();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_search_button,menu);
        initSearch(menu);
        return true;
    }


    private void initSearch(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.search_view);
        mSearchView = (SearchView) searchItem.getActionView();
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                rootLayout.changeToolBarTitleVisibility();
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });
    }

    private final void finshThis(){
        finish();
    }

    @NonNull
    @Override
    public Loader<ImportantPageStatus> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new ImportantPageLoader(this,"北京",NowUserName);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ImportantPageStatus> loader, ImportantPageStatus importantPageStatus) {
        if (importantPageStatus.getWaring()){
            //如果存在警告显示警告列表
            rootLayout.setWarningBoxVisibility(true);
            RecyclerView recyclerView_waring = rootLayout.getWaningListView();
            //获取警告列表
            recyclerView_waring.setAdapter(new ImportantWaringAdapter(importantPageStatus.getWaringList()));
        }
        RecyclerView recyclerView = rootLayout.getListView();
        //设置数据列表
        recyclerView.setAdapter(new ImportantDataAdapter(importantPageStatus.getDataList()));
        //设置表头
        LinearLayout view= (LinearLayout) LayoutInflater.from(this).inflate(R.layout.important_data_list_header,null,false );
        rootLayout.setHeader(view);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ImportantPageStatus> loader) {

    }
}
