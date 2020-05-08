package com.example.smartagriculture.Model.Important;

public class ImportantDataListItem {
    private Integer id;
    private String address;
    private String temperature;
    private String humidity;
    private String air;
    private String status;


    public ImportantDataListItem(Integer id, String address, String temperature, String humidity, String air, String status) {
        this.id = id;
        this.address = address;
        this.temperature = temperature;
        this.humidity = humidity;
        this.air = air;
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getAir() {
        return air;
    }

    public String getStatus() {
        return status;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ImportantDataListItem{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", temperature='" + temperature + '\'' +
                ", humidity='" + humidity + '\'' +
                ", air='" + air + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
