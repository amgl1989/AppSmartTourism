package smarttourism.pfc.uca.es.appsmarttourism;

import java.io.Serializable;
import java.util.List;

/**
 * Created by AnaMari on 14/05/2016.
 */
public class TypeRestaurantPreferenceList implements Serializable {
    private List<TypeRestaurantPreference> typerestaurantPreferenceArrayList;

   // public TypeRestaurantPreferenceList() {}

    public TypeRestaurantPreferenceList(List<TypeRestaurantPreference> typerestaurantPreferenceArrayList) {
        this.typerestaurantPreferenceArrayList = typerestaurantPreferenceArrayList;
    }

    public List<TypeRestaurantPreference> getTyperestaurantPreferenceArrayList() {
        return typerestaurantPreferenceArrayList;
    }
}
