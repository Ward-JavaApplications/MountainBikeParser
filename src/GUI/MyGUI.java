package GUI;

import javax.swing.*;

public class MyGUI {

    private JFrame mainFrame;

    public MyGUI(){
        mainFrame = buildFrame();
        instantiate();
    }
    private JFrame buildFrame(){
        JFrame frame = new JFrame("Mountain Bike Parser");
        frame.setSize(500,530);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return frame;
    }
    public void instantiate(){
        mainFrame.setContentPane(new MyMainPanel(mainFrame,this));
        mainFrame.revalidate();
        //SwingUtilities.updateComponentTreeUI(mainFrame);
    }
    public void refresh(){
        //SwingUtilities.updateComponentTreeUI(mainFrame);
        mainFrame.revalidate();
    }




}
