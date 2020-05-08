package com.example.smartagriculture.Model.Cover;

public class CoverDataListItem {
    private Integer id;
    private String Address;
    private String Status;

    public CoverDataListItem(Integer id, String address, String status) {
        this.id = id;
        Address = address;
        Status = status;
    }

    public String getAddress() {
        return Address;
    }

    public String getStatus() {
        return Status;
    }

    @Override
    public String toString() {
        return "CoverDataListItem{" +
                "id=" + id +
                ", Address='" + Address + '\'' +
                ", Status='" + Status + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }
}
