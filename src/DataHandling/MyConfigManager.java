package DataHandling;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MyConfigManager {
    Properties properties = new Properties();
    String configFilePath = "src/config.properties";
    private Map<String,String> cache = new HashMap<>();

    public MyConfigManager(){
        properties = new Properties();
        populateCacheFromProperties();
    }

    private void populateCacheFromProperties(){
        try {
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

    public void storeProperty(String key,String value){
        try {
            OutputStream outputStream = new FileOutputStream(configFilePath);
            properties.setProperty(key, String.valueOf(value));
            properties.store(outputStream,null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void storeProperty(String key,long value){
        try {
            OutputStream outputStream = new FileOutputStream(configFilePath);
            properties.setProperty(key, String.valueOf(value));
            properties.store(outputStream,null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void storeProperty(String key,int value){
        try {
            OutputStream outputStream = new FileOutputStream(configFilePath);
            properties.setProperty(key, String.valueOf(value));
            properties.store(outputStream,null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public long getPropertyLong(String key){
        try{
            String valueString = getFromProperties(key);
            return Long.parseLong(valueString);
        }
        catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    public int getPropertyInt(String key){
        try{
            String valueString = getFromProperties(key);
            return Integer.parseInt(valueString);
        }
        catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    public String getPropertyString(String key){
        try{
            return getFromProperties(key);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    private String getFromProperties(String key){
        if(cache.containsKey(key)) return cache.get(key);
        else {
            try {
                InputStream inputStream = new FileInputStream(configFilePath);
                properties.load(inputStream);
                String valueString = properties.getProperty(key);
                return valueString;
            }
            catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }
}
