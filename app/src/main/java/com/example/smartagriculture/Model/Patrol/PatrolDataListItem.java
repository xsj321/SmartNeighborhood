package com.example.smartagriculture.Model.Patrol;


public class PatrolDataListItem {
    private String workerName;
    private String date;
    private String way;

    public PatrolDataListItem(String workerName, String date, String way) {
        this.workerName = workerName;
        this.date = date;
        this.way = way;
    }

    public String getWorkerName() {
        return workerName;
    }

    public String getDate() {
        return date;
    }

    public String getWay() {
        return way;
    }

    @Override
    public String toString() {
        return "PatrolDataList{" +
                "workerName='" + workerName + '\'' +
                ", date='" + date + '\'' +
                ", way='" + way + '\'' +
                '}';
    }
}
