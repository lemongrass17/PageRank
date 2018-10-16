import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RefsParser {

    private SiteContainer sc;
    private String domain;
    //private ArrayList<SiteContainer> sc;
    //private String startAdr

    public RefsParser(){
        this.domain = "";
        this.sc = new SiteContainer();
        //this.startAdr = "";
    }

    public RefsParser(SiteContainer sc, String domain){
        this.sc = sc;
        this.domain = domain;
        //this.startAdr = startAdr;
    }

    public void setSC(SiteContainer sc){
        this.sc = sc;
    }

    public SiteContainer getSC(){
        return sc;
    }

    public String getDomain(){
        return domain;
    }

    public void setDomain(String domain){
        this.domain = domain;
    }

    private boolean checkWithRegExp(String address){
        Pattern p = Pattern.compile("(https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|www\\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9]\\.[^\\s]{2,}|www\\.[a-zA-Z0-9]\\.[^\\s]{2,})");
        Matcher m = p.matcher(address);
        return m.matches();
    }

    private String getMainPage(String address){
        try {
            return new URI(address).getHost().replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)","");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void parse(String address){
        if (address.trim().charAt(0) == '/'){
            address = domain + address;
        }
        if (address.trim().charAt(address.length() - 1) != '/'){
            address = address + "/";
        }
        if (!sc.isContainsKey(address) && !address.contains("javascript:void(0);")
                && !address.contains(".png") && !address.contains(".jpg") && !address.contains(".txt") && !address.contains(".gif")
                && getMainPage("http://" + address).equals(domain)) {
            sc.addKey(address);
            try {
                System.out.println(address);
                Document doc = Jsoup.connect("http://" + address).get();
                Elements link = doc.select("a");
                for (Element e : link) {
                    if (!e.attr("href").contains("@") && !e.attr("href").contains("#")) {
                        if (!sc.isContainsLinkByKey(address, e.attr("href"))) {
                            sc.addLinkByKey(address, e.attr("href"));
                        }
                    }
                }
                for (String str : sc.getLinksByKey(address)) {
                    if (str != "") {
                        parse(str.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", ""));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        RefsParser p = new RefsParser();
        //System.out.println(p.checkWithRegExp("https://stopgame.ru/newsdata/35907"));
        //System.out.println(p.getMainPage("https://stopgame.ru/click/?http://www.liveinternet.ru/click"));
        //Connection.Response r = Jsoup.connect("https://stopgame.ru/click/?http://www.liveinternet.ru/click").followRedirects(true).execute();
        //Connection.Response r = Jsoup.connect("http://stopgame.ru/newsdata/35907").followRedirects(true).execute();
        //System.out.println(new URI("https://www.kali.org/").getHost());
        p.setDomain(p.getMainPage("https://gri-software.com/ru/"));
        //System.out.println(p.getMainPage("https://www.kali.org/"));
        p.parse("gri-software.com/ru/");
        //System.out.println("//vk.com/lostfilmtv_official".replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)",""));
    }
}
