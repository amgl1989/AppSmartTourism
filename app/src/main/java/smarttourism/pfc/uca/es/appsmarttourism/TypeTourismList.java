package smarttourism.pfc.uca.es.appsmarttourism;

import java.io.Serializable;
import java.util.List;

/**
 * Created by AnaMari on 20/02/2016.
 */
public class TypeTourismList implements Serializable {
    private List<TypeTourism> typeTourismArrayList;

    public TypeTourismList(List<TypeTourism> typeTourismArrayList) {
        this.typeTourismArrayList = typeTourismArrayList;
    }

    public List<TypeTourism> getTypeTourismArrayList() {
        return typeTourismArrayList;
    }

}
