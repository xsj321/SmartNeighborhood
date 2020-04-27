package com.example.smartagriculture.View.CustomViews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartagriculture.R;

import org.w3c.dom.Text;

import java.util.zip.Inflater;

/**
 * TODO: document your custom view class.
 */
public class DataTable extends LinearLayout {
    private Toolbar toolbar;
    private Context context;
    public DataTable(Context context) {
        super(context);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.data_table,this);
    }

    public DataTable(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.data_table,this);

    }

    public DataTable(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.data_table,this);
    }

    public DataTable(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.data_table,this);
    }


    public Toolbar getToolbar() {
        Toolbar  toolbar = findViewById(R.id.view_tool_bar);
        return toolbar;
    }

    public void setPageTitle(String title){
        TextView textView = findViewById(R.id.the_title);
        textView.setText(title);
    }

    public void setWarningTitle(String title){
        TextView textView = findViewById(R.id.warning_title);
        textView.setText(title);
    }

    public void setWarningBoxVisibility(Boolean is){
        LinearLayout linearLayout =  findViewById(R.id.warning_box);
        if (is){
            linearLayout.setVisibility(VISIBLE);
        }else {
            linearLayout.setVisibility(GONE);
        }
    }

    public RecyclerView getWaningListView(){
        RecyclerView recyclerView = findViewById(R.id.warning_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        return recyclerView;
    }

    public void changeToolBarTitleVisibility(){
        TextView textView  = findViewById(R.id.the_title);
        int vis = textView.getVisibility();
        if (vis == GONE){
            textView.setVisibility(VISIBLE);
        }
        else {
            textView.setVisibility(GONE);
        }

    }

    public void setHeader(LinearLayout layout){
        LinearLayout linearLayout = findViewById(R.id.header);
        LinearLayout theputLayout = layout.findViewById(R.id.list_header);
        linearLayout.addView(theputLayout);
    }

    public MenuItem getSearchView(){
       MenuItem menuItem =  findViewById(R.id.search_view);
       return menuItem;
    }

    public RecyclerView getListView(){
        RecyclerView recyclerView = findViewById(R.id.data_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        return recyclerView;
    }


    public TextView getDataTitleView(){
        return findViewById(R.id.data_title);
    }

}
