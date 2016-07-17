package smarttourism.pfc.uca.es.appsmarttourism;

import java.io.Serializable;

/**
 * Created by AnaMari on 18/03/2016.
 */
public class TypeFoodPreference implements Serializable{
    private String idtypefood;
    private String username;

    public TypeFoodPreference(){}

    public TypeFoodPreference(String idtypefood, String username) {
        this.idtypefood = idtypefood;
        this.username = username;
    }

    public String getIdtypefood() {
        return idtypefood;
    }

    public void setIdtypefood(String idtypefood) {
        this.idtypefood = idtypefood;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
