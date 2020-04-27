package com.example.smartagriculture.Model;

import java.util.ArrayList;

/**
 * 用来传递ImportantPage页面的消息
 */
public class ImportantPageStatus {
    private Boolean isWaring;
    private ArrayList<ImportantDataListItem>  dataList;
    private ArrayList<CoverDataListItem> waringList;

    public ImportantPageStatus(Boolean isWaring, ArrayList<ImportantDataListItem> dataList, ArrayList<CoverDataListItem> waringList) {
        this.isWaring = isWaring;
        this.dataList = dataList;
        this.waringList = waringList;
    }

    public Boolean getWaring() {
        return isWaring;
    }

    public ArrayList<ImportantDataListItem> getDataList() {
        return dataList;
    }

    public ArrayList<CoverDataListItem> getWaringList() {
        return waringList;
    }
}
