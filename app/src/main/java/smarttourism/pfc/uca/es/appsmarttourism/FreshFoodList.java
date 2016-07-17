package smarttourism.pfc.uca.es.appsmarttourism;

import java.io.Serializable;
import java.util.List;

/**
 * Created by AnaMari on 18/02/2016.
 */
public class FreshFoodList implements Serializable {
    private List<FreshFood> freshFoodArrayList;

    public FreshFoodList(List<FreshFood> freshFoodArrayList) {
        this.freshFoodArrayList = freshFoodArrayList;
    }

    public List<FreshFood> getFreshFoodArrayList() {
        return freshFoodArrayList;
    }
}
