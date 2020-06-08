package com.example.smartagriculture.View.Important;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import com.example.smartagriculture.Model.Important.ImportantPageStatus;
import com.example.smartagriculture.Service.important.ImportantService;

public class ImportantPageLoader extends AsyncTaskLoader<ImportantPageStatus> {

    private String location;
    private ImportantService service;
    private Context context;
    public ImportantPageLoader(@NonNull Context context,String location) {
        super(context);
        this.context = context;
        this.location = location;
        service = new ImportantService(context);
    }

    @Nullable
    @Override
    public ImportantPageStatus loadInBackground() {
        return service.getImportantData(location);
    }
}
