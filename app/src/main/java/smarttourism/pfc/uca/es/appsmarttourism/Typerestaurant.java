package smarttourism.pfc.uca.es.appsmarttourism;

import java.io.Serializable;

/**
 * Created by AnaMari on 15/02/2016.
 */
public class Typerestaurant implements Serializable{
    private String id;
    private String typeRestaurant;
    // private List<Restaurant> restaurantList = new ArrayList<Restaurant>();

    public Typerestaurant() {}

    public Typerestaurant(String id, String typeRestaurant) {
        this.id = id;
        this.typeRestaurant = typeRestaurant;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeRestaurant() {
        return this.typeRestaurant;
    }

    public void setTypeRestaurant(String typeRestaurant) {
        this.typeRestaurant = typeRestaurant;
    }
}