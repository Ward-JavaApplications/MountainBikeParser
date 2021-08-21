package DataHandling;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MyConfigManager {
    private final static String configFilePath = "src/config.properties";
    private static final Map<String,String> cache = new HashMap<>();

    public static final String waitTimeBeforeRefreshKey = "WAIT_TIME_BEFORE_REFRESH";
    public static final String urlKey = "URL";

    public MyConfigManager(){
        populateCacheFromProperties();
    }

    private static void populateCacheFromProperties(){
        try {
            Properties properties = new Properties();
            InputStream inputStream = new FileInputStream(configFilePath);
            properties.load(inputStream);
            for (Map.Entry<Object, Object> objectObjectEntry : properties.entrySet()) {
                cache.put(objectObjectEntry.getKey().toString(), objectObjectEntry.getValue().toString());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void storeProperty(String key,String value){
        if(cache.containsKey(key)){
            cache.replace(key,value);
        }
        else{
            cache.put(key,value);
        }
    }

    public static void storeProperty(String key,long value){
        storeProperty(key,String.valueOf(value));
    }
    public static void storeProperty(String key,int value){
        storeProperty(key,String.valueOf(value));
    }
    public static long getPropertyLong(String key){
        try {
            return Long.parseLong(getPropertyString(key));
        }
        catch (Exception e){
            return 0;
        }
    }
    public static int getPropertyInt(String key){
        try {
            return Integer.parseInt(getPropertyString(key));
        }
        catch (Exception e){
            return 0;
        }
    }
    public static String getPropertyString(String key){
        return cache.get(key);
    }
    public static void saveCacheToConfig(){
        try{
            OutputStream outputStream = new FileOutputStream(configFilePath);
            Properties properties = new Properties();
            for(Map.Entry<String,String> entry: cache.entrySet()){
                properties.setProperty(entry.getKey(), entry.getValue());
            }
            properties.store(outputStream,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
