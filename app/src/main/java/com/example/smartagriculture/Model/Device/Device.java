package com.example.smartagriculture.Model.Device;

import android.support.annotation.NonNull;

import org.json.JSONObject;

import java.io.Serializable;

public class Device implements Serializable {
    private String DeviceId;
    private String DeviceName;
    private String DeviceEvent;
    private JSONObject DeviceCom;
    private boolean isOnline;

    public Device(String deviceId, String deviceName, String deviceEvent, JSONObject deviceCom, boolean isOnline) {
        DeviceId = deviceId;
        DeviceName = deviceName;
        DeviceEvent = deviceEvent;
        DeviceCom = deviceCom;
        this.isOnline = isOnline;
    }

    @NonNull
    @Override
    public String toString() {
        String device =
                "Device ID:" + DeviceId + "\n" +
                        "Device Name:" + DeviceName + "\n" +
                        "Device Name:" + DeviceEvent + "\n";
        return device;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getDeviceName() {
        return DeviceName;
    }

    public void setDeviceName(String deviceName) {
        DeviceName = deviceName;
    }

    public String getDeviceEvent() {
        return DeviceEvent;
    }

    public void setDeviceEvent(String deviceEvent) {
        DeviceEvent = deviceEvent;
    }

    public JSONObject getDeviceCom() {
        return DeviceCom;
    }

    public void setDeviceCom(JSONObject deviceCom) {
        DeviceCom = deviceCom;
    }

    public boolean isOnline() {
        return isOnline;
    }
}
