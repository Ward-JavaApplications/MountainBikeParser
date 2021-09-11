package GUI;

import javax.swing.*;
import java.awt.*;

public class Notification {
    private JFrame mainFrame;
    public Notification(String name){
        mainFrame = new JFrame("The status of " + name + " has changed");
        mainFrame.setAlwaysOnTop(true);
        mainFrame.setBackground(Color.YELLOW);
        mainFrame.setSize(700,400);
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
        JLabel nameLabel = new JLabel(name);
        nameLabel.setForeground(Color.RED);
        nameLabel.setFont(nameLabel.getFont().deriveFont(80f));
        mainPanel.add(nameLabel);
        JLabel titleLabel = new JLabel("has become available");
        titleLabel.setForeground(Color.RED);
        titleLabel.setFont(titleLabel.getFont().deriveFont(60f));
        mainPanel.add(titleLabel);
        mainFrame.setContentPane(mainPanel);
    }
    public void closeNotification(){
        mainFrame.dispose();
    }
}
