package dataclasses;

import com.example.invotyx.olacontrols.R;

public class Devices {
    private String device_id;
    private String device_name;
    private int device_icon;
    private int func_type;
    private int chid;
    private int uid;
    private int productId;
    private int application_Subversion;
    private int application_Version;
    private int manufacture_id;
    private int productType;
    private int battery;
    private int dimOnValue;
    private int basicValue;
    private int controlType;
    private String lastBeatTime;
    private int lowBatteryNotify;
    private int mainScene;
    private int mainChannelAssoc;
    private int security;
    private int sensorState;
    private int sensorUnit;
    private int sensorValue;
    private int switchAll;
    private int temperNotify;
    private String code;
    private String lastTemperTime;
    private String homeID;




    public Devices()
    {

        this.device_id="";
        this.device_name="";
        this.device_icon=0;
        this.func_type=0;
        this.chid=0;
        this.uid=0;
        this.productId=0;
        this.application_Subversion=0;
        this.application_Version=0;
        this.manufacture_id=0;
        this.productType=0;
        this.battery=0;

    }
    public Devices(String device_id, String device_name)
    {
        this.device_id=device_id;
        this.device_name=device_name;
        setDeviceIcon();

    }
    public Devices(int funcType)
    {
        this.func_type=funcType;
        setDeviceInfo();

    }
    public Devices(String device_id, int func_type, int chid, int uid)
    {
        this.device_id=device_id;
        this.func_type = func_type;
        this.chid=chid;
        this.uid=uid;
        setDeviceInfo();
    }
    public Devices(String device_id, int func_type, int chid, int uid, int icon_id)
    {
        this.device_id=device_id;
        this.func_type = func_type;
        this.chid=chid;
        this.uid=uid;
        setDeviceInfo();
        this.device_icon = icon_id;
    }

    public void setId(String id)
    {
        this.device_id= id;
    }
    public void setName(String name)
    {
        this.device_name = name;
    }
    public void setApplication_Subversion(int application_Subversion) {
        this.application_Subversion = application_Subversion;
    }
    public void setApplication_Version(int application_Version) {
        this.application_Version = application_Version;
    }

    public void setHomeID(String homeID) {
        this.homeID = homeID;
    }

    public void setBasicValue(int basicValue) {
        this.basicValue = basicValue;
    }
    public void setBattery(int battery) {
        this.battery = battery;
    }
    public void setChid(int chid) {
        this.chid = chid;
    }
    public void setControlType(int controlType) {
        this.controlType = controlType;
    }
    public void setDevice_icon(int device_icon) {
        this.device_icon = device_icon;
    }
    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }
    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }
    public void setDimOnValue(int dimOnValue) {
        this.dimOnValue = dimOnValue;
    }
    public void setFunc_type(int func_type) {
        this.func_type = func_type;
    }
    public void setLastBeatTime(String lastBeatTime) {
        this.lastBeatTime = lastBeatTime;
    }
    public void setLowBatteryNotify(int lowBatteryNotify) {
        this.lowBatteryNotify = lowBatteryNotify;
    }
    public void setMainChannelAssoc(int mainChannelAssoc) {
        this.mainChannelAssoc = mainChannelAssoc;
    }
    public void setMainScene(int mainScene) {
        this.mainScene = mainScene;
    }
    public void setManufacture_id(int manufacture_id) {
        this.manufacture_id = manufacture_id;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public void setProductType(int productType) {
        this.productType = productType;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public void setSecurity(int security) {
        this.security = security;
    }
    public void setLastTemperTime(String lastTemperTime) {
        this.lastTemperTime = lastTemperTime;
    }
    public void setSensorState(int sensorState) {
        this.sensorState = sensorState;
    }
    public void setSensorUnit(int sensorUnit) {
        this.sensorUnit = sensorUnit;
    }
    public void setSensorValue(int sensorValue) {
        this.sensorValue = sensorValue;
    }
    public void setSwitchAll(int switchAll) {
        this.switchAll = switchAll;
    }
    public void setTemperNotify(int temperNotify) {
        this.temperNotify = temperNotify;
    }

    public String getId()
    {
        return this.device_id;
    }
    public String getName()
    {
        return this.device_name;
    }
    public int getIcon()
    {
        return this.device_icon;
    }
    public int getFunc_type() {
        return func_type;
    }
    public int getChid() {
        return chid;
    }
    public int getUid() {
        return this.uid;
    }
    public int getApplication_Subversion() {
        return application_Subversion;
    }
    public int getApplication_Version() {
        return application_Version;
    }
    public int getBasicValue() {
        return basicValue;
    }
    public int getBattery() {
        return battery;
    }
    public int getControlType() {
        return controlType;
    }
    public int getDevice_icon() {
        return device_icon;
    }
    public int getDimOnValue() {
        return dimOnValue;
    }
    public int getLowBatteryNotify() {
        return lowBatteryNotify;
    }
    public int getMainChannelAssoc() {
        return mainChannelAssoc;
    }
    public int getMainScene() {
        return mainScene;
    }
    public int getManufacture_id() {
        return manufacture_id;
    }
    public int getProductId() {
        return productId;
    }

    public int getProductType() {
        return productType;
    }

    public int getSecurity() {
        return security;
    }

    public int getSensorState() {
        return sensorState;
    }

    public String getDevice_id() {
        return device_id;
    }

    public String getDevice_name() {
        return device_name;
    }

    public String getLastBeatTime() {
        return lastBeatTime;
    }

    public int getSensorUnit() {
        return sensorUnit;
    }

    public int getSensorValue() {
        return sensorValue;
    }

    public int getSwitchAll() {
        return switchAll;
    }

    public int getTemperNotify() {
        return temperNotify;
    }

    public String getCode() {
        return code;
    }

    public String getLastTemperTime() {
        return lastTemperTime;
    }

    public String getHomeID() {
        return homeID;
    }

    public void setDeviceIcon()
    {
        switch (device_name)
        {
            case "Illumination Sensor" : this.device_icon =  R.drawable.mainscreen_illumination;
            break;
            case "Camera" : this.device_icon = R.drawable.mainscreen_camera;
            break;
            case "Temperature Sensor" : this.device_icon = R.drawable.mainscreen_smokesensor;
            break;
            case "Alarm" : this.device_icon = R.drawable.mainscreen_siren;
            break;
            case "PIR Detector" : this.device_icon = R.drawable.mainscreen_pir;
                break;
            case "Add device" : this.device_icon = R.drawable.macro_add;
                break;
            default: this.device_icon = R.drawable.mainscreen_camera;
        }
    }
    public void setDeviceInfo()
    {
        switch (func_type)
        {
            case 11 : {
                this.device_name = "Temerature";
                this.device_icon = R.drawable.mainscreen_temperature;
                break;
            }
            case 12 : {
                this.device_name = "Illumination";
                this.device_icon = R.drawable.mainscreen_illumination;
                break;
            }
            case 13 : {
                this.device_name = "Door/Window";
                this.device_icon = R.drawable.mainscreen_door;
                break;
            }
            case 14 : {
                this.device_name = "Motion Sensor";
                this.device_icon = R.drawable.mainscreen_pir;
                break;
            }
            case 15 : {
                this.device_name = "Humidity";
                this.device_icon = R.drawable.mainscreen_humidity;
                break;
            }
            case 16 : {
                this.device_name = "GPIO";
                this.device_icon = R.drawable.mainscreen_illumination;
                break;
            }
            case 17 : {
                this.device_name = "Smoke Sensor";
                this.device_icon = R.drawable.mainscreen_smokesensor;
                break;
            }
            case 18 : {
                this.device_name = "CO Sensor";
                this.device_icon = R.drawable.mainscreen_illumination;
                break;
            }
            case 19 : {
                this.device_name = "CO2 Sensor";
                this.device_icon = R.drawable.mainscreen_illumination;
                break;
            }
            case 20 : {
                this.device_name = "Flood";
                this.device_icon = R.drawable.mainscreen_illumination;
                break;
            }
            case 21 : {
                this.device_name = "Glass Break";
                this.device_icon = R.drawable.mainscreen_illumination;
                break;
            }
            case 22 : {
                this.device_name = "Meter Switch";
                this.device_icon = R.drawable.mainscreen_siren;
                break;
            }
            case 23 : {
                this.device_name = "Switch";
                this.device_icon = R.drawable.mainscreen_illumination;
                break;
            }
            case 24 : {
                this.device_name = "Dimmer";
                this.device_icon = R.drawable.mainscreen_illumination;
                break;
            }
            case 25 : {
                this.device_name = "";
                this.device_icon = R.drawable.mainscreen_illumination;
                break;
            }
            case 26 : {
                this.device_name = "Curtain";
                this.device_icon = R.drawable.mainscreen_illumination;
                break;
            }
            case 27 : {
                this.device_name = "Remote";
                this.device_icon = R.drawable.mainscreen_illumination;
                break;
            }
            case 28 : {
                this.device_name = "Button";
                this.device_icon = R.drawable.mainscreen_illumination;
                break;
            }
            case 29 : {
                this.device_name = "Meter Sensor";
                this.device_icon = R.drawable.mainscreen_illumination;
                break;
            }
            case 30 : {
                this.device_name = "Meter Dimmer";
                this.device_icon = R.drawable.mainscreen_illumination;
                break;
            }
            case -1 : {
                this.device_name = "Add Device";
                this.device_icon = R.drawable.macro_add;
                break;
            }

            default:  {
                this.device_name = "Unknown";
                this.device_icon = R.drawable.macro_add;
                break;
            }
        }
    }


    void setDeviceIcon(int icon_id)
    {
        icon_id = R.drawable.available_devices_bg;
        this.device_icon = icon_id;
    }


}

