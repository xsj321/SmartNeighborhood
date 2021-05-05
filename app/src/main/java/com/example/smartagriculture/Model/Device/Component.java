package com.example.smartagriculture.Model.Device;

import java.io.Serializable;

public class Component implements Serializable {
    private String componentName;
    private String type;
    private String value;

    public Component(String componentName, String type, String value) {
        this.componentName = componentName;
        this.type = type;
        this.value = value;
    }

    public Component(String componentName, String type, int value) {
        this.componentName = componentName;
        this.type = type;
        this.value = String.valueOf(value);
    }

    public Component(String componentName, String type, boolean value) {
        this.componentName = componentName;
        this.type = type;
        this.value = String.valueOf(value);
    }


    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
