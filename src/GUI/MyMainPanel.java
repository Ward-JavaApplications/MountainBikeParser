package GUI;

import DataHandling.MyConfigManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyMainPanel extends JPanel {

    private final int minDuration = 10;
    private MyConfigManager configManager;
    private final String waitTimeBeforeRefreshKey = "WAIT_TIME_BEFORE_REFRESH";
    private final String urlKey = "URL";


    public MyMainPanel(JFrame mainFrame){
        super(new SpringLayout());
        configManager = new MyConfigManager();
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        instantiate(mainFrame);
    }
    private void instantiate(JFrame mainFrame){

        add(getURLPanel(mainFrame));
        add(getFrequencyPanel(mainFrame));
    }

    private JPanel getURLPanel(JFrame mainFrame){
        String url = getURL();
        JLabel urlLabelText = new JLabel("Currently checking the following url:");
        JLabel urlLabelURL = new JLabel(url);
        urlLabelURL.setForeground(Color.GRAY);
        JButton changeURL;
        String urlMessage;
        if(url == null){
            //no url was given yet
            changeURL = new JButton("Add url");
            urlMessage = "Type the url you would like to check in the box below";
        }
        else{
            changeURL = new JButton("Change url");
            urlMessage = "Type the new url in the box below";
        }
        changeURL.addActionListener(action -> {
            changeURL.setText("Change url");
            String answer = JOptionPane.showInputDialog(urlMessage,"");
            urlLabelURL.setText(answer);
            configManager.storeProperty(urlKey,answer);
                });
        JPanel urlPanel = new JPanel(new SpringLayout());
        urlPanel.setLayout(new BoxLayout(urlPanel,BoxLayout.Y_AXIS));
        urlPanel.add(urlLabelText);
        urlPanel.add(urlLabelURL);
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(changeURL);
        urlPanel.add(buttonPanel);
        return urlPanel;
    }

    private JPanel getFrequencyPanel(JFrame mainFrame){
        JPanel frequencyPanel = new JPanel(new SpringLayout());
        frequencyPanel.setLayout(new BoxLayout(frequencyPanel,BoxLayout.Y_AXIS));
        JLabel changeFrequencyLabel = new JLabel("Change Frequency");
        int oldSliderPosition = getSliderPositionFromSave();
        JLabel frequencyConfirmLabel = new JLabel(durationToText(getDuration(oldSliderPosition)));
        JSlider slider = new JSlider(0,1000);
        slider.setValue(oldSliderPosition);
        slider.addChangeListener(changeEvent -> {
            try {
                int value = slider.getValue();
                long duration = getDuration(value);
                frequencyConfirmLabel.setText(durationToText(duration));
                configManager.storeProperty(waitTimeBeforeRefreshKey,value);
                //SwingUtilities.updateComponentTreeUI(mainFrame);
            }
            catch (Exception exception){
                exception.printStackTrace();
            }
        });
        frequencyPanel.add(changeFrequencyLabel);
        frequencyPanel.add(slider);
        frequencyPanel.add(frequencyConfirmLabel);
        return frequencyPanel;
    }

    private String getURL(){
        return configManager.getPropertyString(urlKey);
    }
    private int getSliderPositionFromSave(){
        return configManager.getPropertyInt(waitTimeBeforeRefreshKey);
    }
    private long getDuration(int sliderValue){
        //f(x) = exp(0.008188689*x)
        double a = 0.008188689D;
        double duration = Math.exp(a*sliderValue);
        return Math.round(duration) + minDuration;
    }
    private String durationToText(long duration){
        //receives the duration in seconds
        String frequencyConfrimText = "The website will be checked every ";
        if(duration<60){
            return frequencyConfrimText + duration + " seconds";
        }
        else if(duration<60*60){
            return frequencyConfrimText + duration/60 + " minutes";
        }
        else return frequencyConfrimText + duration/(60*60) + " hours";
    }
}
