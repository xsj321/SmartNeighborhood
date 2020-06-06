package com.example.smartagriculture.View.Cover;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.smartagriculture.Model.Cover.CoverPageStatus;
import com.example.smartagriculture.Service.cover.CoverService;

public class CoverPageLoader extends AsyncTaskLoader<CoverPageStatus> {
    private String location;
    private CoverService service;
    private Context  context;
    public CoverPageLoader(@NonNull Context context, String location) {
        super(context);
        this.location = location;
        this.context = context;
        service = new CoverService();
        this.location = location;
    }

    @Nullable
    @Override
    public CoverPageStatus loadInBackground() {
        return service.getCoverData(context, location);
    }

}
