import DataHandling.DataSaverOnExit;
import DataHandling.MyConfigManager;
import GUI.MyGUI;

public class Demo {
    public static void main(String[] args) {
        new MyConfigManager();
        Thread shutDownThread = new Thread(new DataSaverOnExit());
        Runtime.getRuntime().addShutdownHook(shutDownThread);
        new MyGUI();
    }
}
