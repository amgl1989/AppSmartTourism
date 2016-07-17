package smarttourism.pfc.uca.es.appsmarttourism;

import java.io.Serializable;
import java.util.List;

/**
 * Created by AnaMari on 14/05/2016.
 */
public class TypeFoodPreferenceList implements Serializable {
    private List<TypeFoodPreference> typefoodPreferenceArrayList;

    public TypeFoodPreferenceList(){ }

    public TypeFoodPreferenceList(List<TypeFoodPreference> typefoodPreferenceArrayList) {
        this.typefoodPreferenceArrayList = typefoodPreferenceArrayList;
    }

    public List<TypeFoodPreference> getTypefoodPreferenceArrayList() {
        return typefoodPreferenceArrayList;
    }
}
