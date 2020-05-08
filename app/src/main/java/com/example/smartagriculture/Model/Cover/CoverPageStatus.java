package com.example.smartagriculture.Model.Cover;

import com.example.smartagriculture.Model.Important.ImportantDataListItem;

import java.util.ArrayList;

public class CoverPageStatus {
    private Boolean isWaring;
    private ArrayList<CoverDataListItem> dataList;
    private ArrayList<CoverDataListItem> waringList;

    public CoverPageStatus(Boolean isWaring, ArrayList<CoverDataListItem> dataList, ArrayList<CoverDataListItem> waringList) {
        this.isWaring = isWaring;
        this.dataList = dataList;
        this.waringList = waringList;
    }

    @Override
    public String toString() {
        return "CoverPageStatus{" +
                "isWaring=" + isWaring +
                ", dataList=" + dataList +
                ", waringList=" + waringList +
                '}';
    }

    public Boolean getWaring() {
        return isWaring;
    }

    public ArrayList<CoverDataListItem> getDataList() {
        return dataList;
    }

    public ArrayList<CoverDataListItem> getWaringList() {
        return waringList;
    }
}
