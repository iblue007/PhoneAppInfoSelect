package com.xqx.phoneappinfoselect;

import java.lang.reflect.Method;

/**手机os 获取类
 * Created by xuqunxing on 2017/11/7.
 */
public class PhonOSUtil {

    public static PhonOSUtil instance = null;
    public static PhonOSUtil getInstance(){
        if(instance == null){
            instance = new PhonOSUtil();
        }
        return instance;
    }
    /**获取手机的os*/
    public static String getPhoneOS(String brand){
        String systemProperties = null;
        if(PhoneOS.HUAWEI.getBrand().equals(brand.toLowerCase())){
            systemProperties = PhonOSUtil.getSystemPropertiesOS(PhoneOS.HUAWEI.getCmdPropName());
        }else if(PhoneOS.HONOR.getBrand().equals(brand.toLowerCase())){
            systemProperties = PhonOSUtil.getSystemPropertiesOS(PhoneOS.HONOR.getCmdPropName());
        }else if(PhoneOS.XIAOMI.getBrand().equals(brand.toLowerCase())){
            systemProperties = PhonOSUtil.getSystemPropertiesOS(PhoneOS.XIAOMI.getCmdPropName());
        }else if(PhoneOS.OPPO.getBrand().equals(brand.toLowerCase())){
            systemProperties = PhonOSUtil.getSystemPropertiesOS(PhoneOS.OPPO.getCmdPropName());
        }else if(PhoneOS.VIVO.getBrand().equals(brand.toLowerCase())){
            systemProperties = PhonOSUtil.getSystemPropertiesOS(PhoneOS.VIVO.getCmdPropName());
        }else if(PhoneOS.GIONEE.getBrand().equals(brand.toLowerCase())){
            systemProperties = PhonOSUtil.getSystemPropertiesOS(PhoneOS.GIONEE.getCmdPropName());
        }else if(PhoneOS.MEIZHU.getBrand().equals(brand.toLowerCase())){
            systemProperties = PhonOSUtil.getSystemPropertiesOS(PhoneOS.MEIZHU.getCmdPropName());
        }else if(PhoneOS.ZTE.getBrand().equals(brand.toLowerCase())){
            systemProperties = PhonOSUtil.getSystemPropertiesOS(PhoneOS.ZTE.getCmdPropName());
        }else if(PhoneOS.COOLPAD.getBrand().equals(brand.toLowerCase())){
            systemProperties = PhonOSUtil.getSystemPropertiesOS(PhoneOS.COOLPAD.getCmdPropName());
        }else if(PhoneOS.SAMSUNG.getBrand().equals(brand.toLowerCase())){
            systemProperties = PhonOSUtil.getSystemPropertiesOS(PhoneOS.SAMSUNG.getCmdPropName());
        }else if(PhoneOS.HTC.getBrand().equals(brand.toLowerCase())){
            systemProperties = PhonOSUtil.getSystemPropertiesOS(PhoneOS.HTC.getCmdPropName());
        }
        return systemProperties;
    }

    /**获取不同手机对应的OS*/
    public static String getSystemPropertiesOS(String cmdPropName) {
        Class<?> classType = null;
        String buildVersion = null;
        try {
            classType = Class.forName("android.os.SystemProperties");
            Method getMethod = classType.getDeclaredMethod("get", new Class<?>[]{String.class});
            buildVersion = (String) getMethod.invoke(classType, new Object[]{cmdPropName});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buildVersion;
    }

}
