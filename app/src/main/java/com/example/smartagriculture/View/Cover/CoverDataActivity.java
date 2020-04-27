package com.example.smartagriculture.View.Cover;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.smartagriculture.Model.CoverDataListItem;
import com.example.smartagriculture.R;

import java.util.ArrayList;

public class CoverDataActivity extends AppCompatActivity {

    private SearchView mSearchView;
    private DataTable rootLayout;
    public static final ArrayList<CoverDataListItem> datlist =  new ArrayList<>();
    public static final ArrayList<CoverDataListItem> list_waring = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover_data);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.PureWhite));
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        rootLayout = findViewById(R.id.cover_root);
        rootLayout.setPageTitle("窖井盖");
        rootLayout.getDataTitleView().setText(getResources().getText(R.string.cover_data_title));
        rootLayout.setWarningBoxVisibility(true);
        rootLayout.setWarningTitle(getResources().getString(R.string.cover_data_waring_title));
        Toolbar toolbar = rootLayout.getToolbar();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setTitle(null);

        {
            datlist.add(new CoverDataListItem(1, "中山北街窨井盖4号", "异常(疑似开启、丢失)"));
            datlist.add(new CoverDataListItem(1, "中山北街窨井盖4号", "异常(疑似开启、丢失)"));
            datlist.add(new CoverDataListItem(1, "中山北街窨井盖4号", "异常(疑似开启、丢失)"));
            datlist.add(new CoverDataListItem(1, "中山北街窨井盖4号", "正常"));
            datlist.add(new CoverDataListItem(1, "中山北街窨井盖4号", "正常"));
            datlist.add(new CoverDataListItem(1, "中山北街窨井盖4号", "正常"));
            datlist.add(new CoverDataListItem(1, "中山北街窨井盖4号", "正常"));
            datlist.add(new CoverDataListItem(1, "中山北街窨井盖4号", "正常"));
            datlist.add(new CoverDataListItem(1, "中山北街窨井盖4号", "正常"));
            datlist.add(new CoverDataListItem(1, "中山北街窨井盖4号", "正常"));
            datlist.add(new CoverDataListItem(1, "中山北街窨井盖4号", "正常"));
            datlist.add(new CoverDataListItem(1, "中山北街窨井盖4号", "正常"));
            datlist.add(new CoverDataListItem(1, "中山北街窨井盖4号", "正常"));
            datlist.add(new CoverDataListItem(1, "中山北街窨井盖4号", "正常"));

        }

        {
            list_waring.add(new CoverDataListItem(1, "中山北街窨井盖4号", "异常(疑似开启、丢失)"));
            list_waring.add(new CoverDataListItem(1, "中山北街窨井盖4号", "异常(疑似开启、丢失)"));
            list_waring.add(new CoverDataListItem(1, "中山北街窨井盖4号", "异常(疑似开启、丢失)"));
        }

        RecyclerView recyclerView = rootLayout.getListView();
        recyclerView.setAdapter(new CoverDataAdapter(datlist));
        RecyclerView recyclerView_waring = rootLayout.getWaningListView();
        recyclerView_waring.setAdapter(new CoverWaringAdapter(list_waring));
        LinearLayout view= (LinearLayout) LayoutInflater.from(this).inflate(R.layout.cover_data_list_header,null,false );
        rootLayout.setHeader(view);


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
}
