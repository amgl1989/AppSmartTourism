package smarttourism.pfc.uca.es.appsmarttourism;

import java.io.Serializable;

/**
 * Created by AnaMari on 20/02/2016.
 */
public class TypeTourism implements Serializable {
    private String id;
    private String typetourism;

    public TypeTourism(){}

    public TypeTourism(String id, String typetourism) {
        this.id = id;
        this.typetourism = typetourism;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypetourism() {
        return typetourism;
    }

    public void setTypetourism(String typetourism) {
        this.typetourism = typetourism;
    }
}
