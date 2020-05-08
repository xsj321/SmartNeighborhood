package com.example.smartagriculture.Model.HomePage;

public class HomePageStatus {
    private int temperature;
    private String humidity;
    private int pm2_5;
    private Boolean cover = false;
    private Boolean important = false;

    public HomePageStatus(int  ID, int temperature, String humidity, int pm2_5, Boolean cover, Boolean important) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pm2_5 = pm2_5;
        this.cover = cover;
        this.important = important;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public int getPm2_5() {
        return pm2_5;
    }

    public Boolean getCover() {
        return cover;
    }

    public Boolean getImportant() {
        return important;
    }
}
