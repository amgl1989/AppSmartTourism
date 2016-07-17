package smarttourism.pfc.uca.es.appsmarttourism;

import java.io.Serializable;

/**
 * Created by AnaMari on 18/03/2016.
 */
public class TypeRestaurantPreference implements Serializable{
    private String idtyperestaurant;
    private String username;

    public TypeRestaurantPreference(){}

    public TypeRestaurantPreference(String idtyperestaurant, String username) {
        this.idtyperestaurant = idtyperestaurant;
        this.username = username;
    }

    public String getIdtyperestaurant() {
        return idtyperestaurant;
    }

  /*  public void setIdtyperestaurant(String idtyperestaurant) {
        this.idtyperestaurant = idtyperestaurant;
    }*/

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
