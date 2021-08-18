package GUI;

import javax.swing.*;

public class MyGUI {


    public MyGUI(){
        JFrame mainFrame = buildFrame();
        instantiate(mainFrame);
    }
    private JFrame buildFrame(){
        JFrame frame = new JFrame("Mountain Bike Parser");
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return frame;
    }
    private void instantiate(JFrame mainFrame){
        mainFrame.setContentPane(new MyMainPanel(mainFrame));
        SwingUtilities.updateComponentTreeUI(mainFrame);
    }

}
