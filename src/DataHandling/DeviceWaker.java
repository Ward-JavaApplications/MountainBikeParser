package DataHandling;

import java.awt.*;

public class DeviceWaker implements Runnable{
    private boolean toggle;
    @Override
    public void run() {
        try {
            while (true) {
                toggle = !toggle;
                Thread.sleep(10*1000);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

