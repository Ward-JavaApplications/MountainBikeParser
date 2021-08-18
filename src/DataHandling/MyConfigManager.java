package DataHandling;

import java.io.*;
import java.util.Properties;

public class MyConfigManager {
    Properties properties;
    String configFilePath = "src/config.properties";;

    public MyConfigManager(){
        properties = new Properties();
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
            InputStream inputStream = new FileInputStream(configFilePath);
            properties.load(inputStream);
            String valueString = properties.getProperty(key);
            int value = Integer.parseInt(valueString);
            return value;
        }
        catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    public int getPropertyInt(String key){
        try{
            InputStream inputStream = new FileInputStream(configFilePath);
            properties.load(inputStream);
            String valueString = properties.getProperty(key);
            int value = Integer.parseInt(valueString);
            return value;
        }
        catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
