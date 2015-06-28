package com.reto.chacao.beans;

/**
 * Created by Eduardo Luttinger on 25/05/2015.
 */
public class Device extends AppBean {

    private String deviceModel;
    private String deviceType;
    private String osVersion;


    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }
}
