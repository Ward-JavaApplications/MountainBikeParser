package DataHandling;

public class DataSaverOnExit implements Runnable{
    @Override
    public void run() {
        MyConfigManager.saveCacheToConfig();
    }
}
