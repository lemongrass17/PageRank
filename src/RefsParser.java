import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RefsParser {

    private ArrayList<SiteContainer> sc;
    private String startAdr;
    //hashmap treemap

    public RefsParser(){
        this.sc = new ArrayList<>();
        this.startAdr = "";
    }

    public RefsParser(ArrayList<SiteContainer> sc, String startAdr){
        this.sc = sc;
        this.startAdr = startAdr;
    }

    public void setSC(ArrayList<SiteContainer> sc){
        this.sc = sc;
    }

    public ArrayList<SiteContainer> getSC(){
        return sc;
    }

    private boolean checkWithRegExp(String address){
        Pattern p = Pattern.compile("(https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|www\\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9]\\.[^\\s]{2,}|www\\.[a-zA-Z0-9]\\.[^\\s]{2,})");
        Matcher m = p.matcher(address);
        return m.matches();
    }

    private String getMainPage(String address){
        try {
            startAdr = new URI(address).getHost();
            return new URI(address).getHost();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void parse(String address){
        if (checkWithRegExp(address)){
            String domain = getMainPage(address);
            sc.add(new SiteContainer(domain, new ArrayList<>()));
            try {
                Document doc = Jsoup.connect("http://" + domain).get();
                Elements link = doc.select("a");
                for (Element e: link) {
                    if (new URI(Jsoup.connect(e.attr("href")).followRedirects(true).execute().url().toString()).getHost().equals(startAdr)){

                    }
                    //System.out.println(e.attr("href"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        RefsParser p = new RefsParser();
        System.out.println(p.checkWithRegExp("https://stopgame.ru/newsdata/35907"));
        System.out.println(p.getMainPage("https://stopgame.ru/click/?http://www.liveinternet.ru/click"));
        //Connection.Response r = Jsoup.connect("https://stopgame.ru/click/?http://www.liveinternet.ru/click").followRedirects(true).execute();
        Connection.Response r = Jsoup.connect("http://stopgame.ru/newsdata/35907").followRedirects(true).execute();
        System.out.println(r.url());
        //p.parse("https://stopgame.ru/");
    }
}
