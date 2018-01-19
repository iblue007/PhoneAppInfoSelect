package com.xqx.phoneappinfoselect;

/**手机os的枚举
 * Created by xuqunxing on 2017/11/8.
 */
public enum  PhoneOS {

    XIAOMI("ro.miui.ui.version.name","xiaomi"),HUAWEI("ro.build.version.emui","huawei"),OPPO("ro.build.version.opporom","oppo"),
    VIVO("ro.vivo.os.build.display.id","vivo"),GIONEE("ro.build.display.id","gionee"),MEIZHU("ro.build.display.id","meizu"),
    ZTE("ro.build.display.id","zte"),COOLPAD("ro.build.display.id","coolpad"),SAMSUNG("ro.build.display.id","samsung"),
    HTC("ro.build.sense.version","htc"),HONOR("ro.build.version.emui","honor");
//    VIVO("ro.vivo.os.version","VIVO");


    private String cmdPropName ;
    private String brand ;

    private PhoneOS( String cmdPropName , String brand ){
        this.cmdPropName = cmdPropName ;
        this.brand = brand ;
    }

    public String getCmdPropName() {
        return cmdPropName;
    }

    public void setCmdPropName(String cmdPropName) {
        this.cmdPropName = cmdPropName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
