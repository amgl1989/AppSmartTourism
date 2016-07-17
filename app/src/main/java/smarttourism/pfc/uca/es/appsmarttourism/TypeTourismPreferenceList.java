package smarttourism.pfc.uca.es.appsmarttourism;

import java.io.Serializable;
import java.util.List;

/**
 * Created by AnaMari on 14/05/2016.
 */
public class TypeTourismPreferenceList implements Serializable {
    private List<TypeTourismPreference> typetourismPreferenceArrayList;

    public TypeTourismPreferenceList(List<TypeTourismPreference> typetourismPreferenceArrayList) {
        this.typetourismPreferenceArrayList = typetourismPreferenceArrayList;
    }

    public List<TypeTourismPreference> getTypetourismPreferenceArrayList() {
        return typetourismPreferenceArrayList;
    }
}
