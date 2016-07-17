package smarttourism.pfc.uca.es.appsmarttourism;

import java.io.Serializable;
import java.util.List;

/**
 * Created by AnaMari on 15/02/2016.
 */
public class TyperestaurantList implements Serializable{
    private List<Typerestaurant> typerestaurantArrayList;

    public TyperestaurantList(){}

    public TyperestaurantList(List<Typerestaurant> typerestaurantArrayList) {
        this.typerestaurantArrayList = typerestaurantArrayList;
    }

    public List<Typerestaurant> getTyperestaurantArrayList() {
        return typerestaurantArrayList;
    }
}