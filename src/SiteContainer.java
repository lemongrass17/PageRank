import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    public Map<String, ArrayList<String>> getMapOfLinks(){
        return links;
    }

    public void rankOfPages(int numOfIter){
        rank = new HashMap<>();
        double d = 0.85;
        double PRnew;
        for (int i = 0; i < numOfIter; i++) {
            for (String key : links.keySet()) {
                rank.put(key, 1.0);
            }
            for (String link : links.keySet()) {
                PRnew = 0;
                for (String key : links.keySet()) {
                    if (isContainsLinkByKey(key, link)) {
                        PRnew = PRnew + rank.get(link) / links.get(link).size();
                    }
                }
                PRnew = (1 - d) + d * PRnew;
                rank.put(link, PRnew);
            }
        }
        //return rank;
        // System.out.println(rank.toString());
    }

    public String toStr(){
        String res = "";
        for (String key: rank.keySet()){
            res = res + key + " --- " + rank.get(key) + "\n";
        }
        return res;
    }
}
