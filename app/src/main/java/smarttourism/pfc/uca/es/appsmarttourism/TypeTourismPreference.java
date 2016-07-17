package smarttourism.pfc.uca.es.appsmarttourism;

import java.io.Serializable;

/**
 * Created by AnaMari on 18/03/2016.
 */
public class TypeTourismPreference implements Serializable{
    private String idtypetourism;
    private String username;

    public TypeTourismPreference(String idtypetourism, String username) {
        this.idtypetourism = idtypetourism;
        this.username = username;
    }

    public String getIdtypetourism() {
        return idtypetourism;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
