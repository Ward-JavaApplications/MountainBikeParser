package HTML;

import DataHandling.MyConfigManager;
import GUI.MyMainPanel;
import GUI.Notification;
import org.imgscalr.Scalr;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import javax.print.Doc;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class HTMLParser implements Runnable{
    private String url;
    private long interval;
    private JPanel panel;
    private boolean parsing = true;
    private JFrame mainFrame;

    public HTMLParser(JPanel label,JFrame mainFrame){
        this.panel = label;
        this.mainFrame = mainFrame;
        url = MyConfigManager.getPropertyString(MyConfigManager.urlKey);
        interval = MyMainPanel.getDuration(MyConfigManager.getPropertyInt(MyConfigManager.waitTimeBeforeRefreshKey));
    }

    public void stopParsing(){
        parsing = false;
    }

    private class DocumentAndBikesContainer{
        private Document document;
        private ArrayList<Bike> bikes;

        public Document getDocument() {
            return document;
        }

        public ArrayList<Bike> getBikes() {
            return bikes;
        }

        public DocumentAndBikesContainer(Document document, ArrayList<Bike> bikes) {
            this.document = document;
            this.bikes = bikes;
        }
    }
    private DocumentAndBikesContainer loadPage(){
        try {
            Document document = Jsoup.connect(url).timeout(10*1000).get();
            Elements bikeTypes = document.select("div.product-box-item-inner").select("a.vitem");
            ArrayList<Bike> types = new ArrayList<>(bikeTypes.size());
            for (Element bikeType : bikeTypes) {
                String type = bikeType.attr("data-vartext");
                String availability = bikeType.attr("title");
                type = retrieveType(type);
                types.add(new Bike(type, availability));
            }
            return new DocumentAndBikesContainer(document, types);
        }
        catch (SocketTimeoutException ignored){
            System.out.println("SockedTimout exception");
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Something went wrong after loading the website");
            System.exit(0);
            return null;
        }
    }
    private String getAvailabilityFromList(ArrayList<Bike> bikes,String targetType){
        try{
            for (Bike bike : bikes) {
                if(bike.equals(targetType)) return bike.availability;
            }
            throw new Exception("The bike we were looking for wasn't available in the list");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void checkIfChanged(JLabel feedBackLabel,String nameBike){
        DocumentAndBikesContainer documentAndBikesContainer = loadPage();
        if(documentAndBikesContainer != null) {
            ArrayList<Bike> types = documentAndBikesContainer.getBikes();
            String oldType = MyConfigManager.getPropertyString(MyConfigManager.typeKey);
            String availability = getAvailabilityFromList(types, oldType);
            feedBackLabel.setText("Currently " + availability);
            String oldAvailability = MyConfigManager.getPropertyString(MyConfigManager.availabilityKey);
            if (!oldAvailability.equals(availability) && availability.contains("stock")) {
                new Notification(nameBike);
            }
            System.out.println("At " + new Date().toString() + " we found that the " + nameBike + " was " + availability);
        }
    }
    private boolean arrayListContains(ArrayList<Bike> bikes,String type){
        for (Bike bike : bikes) {
            if(bike.getType().equals(type))return true;
        }
        return false;
    }


    @Override
    public void run() {
        try {
            DocumentAndBikesContainer documentAndBikesContainer = loadPage();
            while(documentAndBikesContainer == null) {
                documentAndBikesContainer=loadPage();
                Thread.sleep(3*1000);
                System.out.println("It couldn't load in the first try, I will go again");
            }
            ArrayList<Bike> types = documentAndBikesContainer.getBikes();
            Document document = documentAndBikesContainer.getDocument();
            String oldType = MyConfigManager.getPropertyString(MyConfigManager.typeKey);
            if (oldType == null || !arrayListContains(types,oldType)) {
                //first time
                String[] options = types.stream().map(Bike::getType).toArray(String[]::new);
                int choice = JOptionPane.showOptionDialog(null, "Which of these bikes is the one you would like to follow?", "Select the correct bike", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                        options, options[0]);
                MyConfigManager.storeProperty(MyConfigManager.typeKey, options[choice]);
                MyConfigManager.storeProperty(MyConfigManager.availabilityKey,types.get(0).getAvailability());
                oldType = options[choice];
            }
            //generate JPanel
            mainFrame.getContentPane().remove(panel);
            panel = new JPanel(new SpringLayout());
            panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
            String image = document.select("div>a>img").attr("data-zoom-image").toString();
            Elements elements = document.select("div.c1");
            String model = elements.select(".title").text();
            model = model.substring(model.length()/2);
            String price = document.select(".current-price>span").first().text();
            panel.add(new JLabel("This is the bike that is being checked"));
            panel.add(new JLabel(model + " " + oldType));
            BufferedImage imageImage = ImageIO.read(new URL(image));
            panel.add(new JLabel(new ImageIcon(Scalr.resize(imageImage, Scalr.Mode.FIT_TO_HEIGHT,260))));
            panel.add(new JLabel(price));
            JLabel feedbackLabel = new JLabel("currently " + getAvailabilityFromList(types,oldType));
            panel.add(feedbackLabel);
            mainFrame.add(panel);
            mainFrame.revalidate();
            Thread.sleep(interval * 1000);
            while(parsing){
                try{
                    checkIfChanged(feedbackLabel,model + " " + oldType);
                    Thread.sleep(1000*interval);
                }
                catch (Exception e){
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null,"Something went wrong after loading the website for multiple times");
                    System.exit(0);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            parsing = false;
            JOptionPane.showMessageDialog(null,"Something went wrong whilst loading the website, check the url again");
            System.exit(0);
        }
    }


    private String retrieveType(String type){
        try {
            String style = type.substring(type.length() - 4);
            System.out.println(style);
            return null;
        }
        catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException){
            return type;
        }
    }
    public boolean isParsing(){
        return parsing;
    }
    private class Bike{
        private String type;
        private String availability;

        public Bike(String type, String availability) {
            this.type = type;
            this.availability = availability;
        }

        public String getType() {
            return type;
        }

        public String getAvailability() {
            return availability;
        }

        @Override
        public boolean equals(Object obj) {
            return (obj.equals(type));
        }
    }
}
