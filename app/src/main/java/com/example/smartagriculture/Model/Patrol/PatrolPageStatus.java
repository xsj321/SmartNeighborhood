package com.example.smartagriculture.Model.Patrol;

import java.util.ArrayList;

public class PatrolPageStatus {
    private ArrayList<PatrolDataListItem> dataList;

    public PatrolPageStatus(ArrayList<PatrolDataListItem> dataList) {
        this.dataList = dataList;
    }

    public ArrayList<PatrolDataListItem> getDataList() {
        return dataList;
    }
}
