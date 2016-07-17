package smarttourism.pfc.uca.es.appsmarttourism;

import java.io.Serializable;

/**
 * Created by AnaMari on 17/02/2016.
 */
public class Typefood implements Serializable {
    private String id;
    private String typefood;
    //private List<Restaurant> restaurantList = new ArrayList<Restaurant>();

    public Typefood(){}

    public Typefood(String id, String typefood) {
        this.id = id;
        this.typefood = typefood;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypefood() {
        return typefood;
    }

    public void setTypefood(String typefood) {
        this.typefood = typefood;
    }
}
