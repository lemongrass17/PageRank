import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SiteContainer {

    private Map<String, ArrayList<String>> links;
    private Map<String, Double> rank;

    public SiteContainer(){
        this.links = new HashMap<>();
    }

    public SiteContainer(HashMap<String, ArrayList<String>> links){
        this.links = links;
    }

    public boolean isContainsKey(String key){
        if (links.containsKey(key)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isContainsLinkByKey(String key, String link){
        if (links.get(key).contains(link)){
            return true;
        }
        else{
            return false;
        }
    }

    public void addKey(String key){
        links.put(key, new ArrayList<>());
    }

    public void addLinkByKey(String key, String link){
        links.get(key).add(link);
    }

    public ArrayList<String> getLinksByKey(String key){
        return links.get(key);
    }

    public Set getMapOfLinks(){
        return links.entrySet();
    }

    public void rankOfPages(){
        rank = new HashMap<>();
        double d = 0.85;
        double PRnew = 0;
        for(String key : links.keySet()){
            rank.put(key, 1.0);
        }
        //TODO
    }
}
