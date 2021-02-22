package com.example.smartagriculture.Model.Device;

import android.support.annotation.NonNull;

public class Devcie {
    private String DeviceId;
    private String DeviceName;
    private String DeviceEvent;

    public Devcie(String deviceId, String deviceName, String deviceEvent) {
        DeviceId = deviceId;
        DeviceName = deviceName;
        DeviceEvent = deviceEvent;
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
}
