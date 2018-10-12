import java.util.ArrayList;

public class SiteContainer {

    private String address;
    private ArrayList<String> refs;

    public SiteContainer(){
        this.address = "";
        this.refs = new ArrayList<>();
    }

    public SiteContainer(String address, ArrayList<String> refs){
        this.address = address;
        this.refs = refs;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getAddress(){
        return address;
    }

    public void setRefs(ArrayList<String> refs){
        this.refs = refs;
    }

    public ArrayList<String> getRefs(){
        return refs;
    }

    public void addRef(String ref){
        if (!refs.contains(ref)) {
            refs.add(ref);
        }
    }

    public String getRefByInd(int ind){
        return refs.get(ind);
    }
}
