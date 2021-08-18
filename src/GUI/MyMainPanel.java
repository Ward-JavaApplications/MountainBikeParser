package GUI;

import javax.swing.*;
import java.awt.*;

public class MyMainPanel extends JPanel {
    private int minDuration = 10;
    public MyMainPanel(JFrame mainFrame){
        super(new SpringLayout());
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
        JButton changeURL = new JButton("Change url");
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
        JLabel freqyencyConfirmLabel = new JLabel("");
        JSlider slider = new JSlider(0,1000);
        slider.setValue(getDurationFromSave());
        slider.addChangeListener(changeEvent -> {
            try {
                int value = slider.getValue();
                freqyencyConfirmLabel.setText(durationToText(getDuration(value)));
                //SwingUtilities.updateComponentTreeUI(mainFrame);
            }
            catch (Exception exception){
                exception.printStackTrace();
            }
        });
        frequencyPanel.add(changeFrequencyLabel);
        frequencyPanel.add(slider);
        frequencyPanel.add(freqyencyConfirmLabel);
        return frequencyPanel;
    }

    private String getURL(){
        return new String("https://www.geeksforgeeks.org/java-swing-jslider/");
    }
    private int getDurationFromSave(){
        return 0;
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
