package HTML;

import DataHandling.MyConfigManager;
import GUI.MyMainPanel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.swing.*;

public class HTMLParser implements Runnable{
    private String url;
    private long interval;
    private JLabel label;
    private boolean parsing = true;

    public HTMLParser(JLabel label){
        this.label = label;
        url = MyConfigManager.getPropertyString(MyConfigManager.urlKey);
        interval = MyMainPanel.getDuration(MyConfigManager.getPropertyInt(MyConfigManager.waitTimeBeforeRefreshKey));
    }

    public void stopParsing(){
        parsing = false;
    }


    @Override
    public void run() {
        while (parsing){
            try {
                System.out.println("We parse " + url + " and we load every " + interval);
//                Document document = Jsoup.connect(url).get();
//                String tekst = document.select(".artikel-element-order").first().toString();
//                label.setText(tekst);
//                System.out.println(tekst);
                  Thread.sleep(interval * 1000);
            }
            catch (Exception e){
                e.printStackTrace();
                parsing = false;
                JOptionPane.showMessageDialog(null,"Something went wrong whilst loading the website");
                System.exit(0);
            }
        }
    }
}
