package GUI;

import javax.swing.*;
import java.awt.*;

public class Notification {
    public Notification(String name){
        JFrame mainFrame = new JFrame("The status of " + name + " has changed");
        mainFrame.setSize(300,200);
        mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = ge.getDefaultScreenDevice();
        Rectangle rectangle = device.getDefaultConfiguration().getBounds();
        int x = (int)rectangle.getMaxX() - mainFrame.getWidth();
        int y = (int)rectangle.getMaxY() - mainFrame.getHeight();
        mainFrame.setLocation(x,y);
        mainFrame.setVisible(true);

        JPanel mainPanel = new JPanel(new SpringLayout());
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
        mainPanel.add(new JLabel(name));
        mainPanel.add(new JLabel("has become available"));
        mainFrame.setContentPane(mainPanel);
    }
}
