package GUI;

import DataHandling.MyConfigManager;
import HTML.HTMLParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyMainPanel extends JPanel {

    private final int minDuration = 10;
    private MyGUI parent;
    private HTMLParser htmlParser;

    private int sliderValue;
    private String urlValue;


    public MyMainPanel(JFrame mainFrame,MyGUI parent){
        super(new SpringLayout());
        this.parent = parent;
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        instantiate(mainFrame);
        sliderValue = getSliderPositionFromSave();
        urlValue = getURL();
    }
    private void instantiate(JFrame mainFrame){

        add(getURLPanel(mainFrame));
        add(getFrequencyPanel(mainFrame));
        add(getParsingPanel());
    }

    private void reloadPanel(){
        parent.instantiate();
    }

    private JPanel getParsingPanel(){
        JPanel parsingPanel = new JPanel(new FlowLayout());
        JButton startParsingButton = new JButton("Start program");
        JLabel parserFeedBackLabel = new JLabel("Program isn't running");
        startParsingButton.addActionListener(action-> {
            MyConfigManager.storeProperty(MyConfigManager.urlKey,urlValue);
            MyConfigManager.storeProperty(MyConfigManager.waitTimeBeforeRefreshKey,sliderValue);
            htmlParser = new HTMLParser(parserFeedBackLabel);
            new Thread(htmlParser).start();
            parsingPanel.removeAll();
            JButton stopParsingButton = new JButton("Stop program");
            stopParsingButton.addActionListener(action2 -> {
                htmlParser.stopParsing();
                reloadPanel();
            });
            parsingPanel.add(stopParsingButton);
            parsingPanel.add(parserFeedBackLabel);
            parent.refresh();
        });
        parsingPanel.add(startParsingButton);
        parsingPanel.add(parserFeedBackLabel);
        return parsingPanel;
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
            changeURL(urlLabelURL, changeURL, urlMessage);
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
                changeFrequency(frequencyConfirmLabel, slider);
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

    private void changeFrequency(JLabel frequencyConfirmLabel, JSlider slider) {
        int value = slider.getValue();
        long duration = getDuration(value);
        frequencyConfirmLabel.setText(durationToText(duration));
        sliderValue = value;
    }

    private void changeURL(JLabel urlLabelURL, JButton changeURL, String urlMessage) {
        changeURL.setText("Change url");
        String answer = JOptionPane.showInputDialog(urlMessage,"");
        urlLabelURL.setText(answer);
        urlValue = answer;
    }

    private String getURL(){
        return MyConfigManager.getPropertyString(MyConfigManager.urlKey);
    }
    private int getSliderPositionFromSave(){
        return MyConfigManager.getPropertyInt(MyConfigManager.waitTimeBeforeRefreshKey);
    }
    public static long getDuration(int sliderValue){
        //f(x) = exp(0.008188689*x)
        double a = 0.008188689D;
        double duration = Math.exp(a*sliderValue);
        return Math.round(duration) + 10;
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
