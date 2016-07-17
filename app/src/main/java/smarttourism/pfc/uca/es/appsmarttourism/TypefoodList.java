package smarttourism.pfc.uca.es.appsmarttourism;

import java.io.Serializable;
import java.util.List;

/**
 * Created by AnaMari on 17/02/2016.
 */
public class TypefoodList implements Serializable {
    private List<Typefood> typefoodArrayList;

  //  public TypefoodList(){}

    public TypefoodList(List<Typefood> typefoodArrayList) {
        this.typefoodArrayList = typefoodArrayList;
    }

    public List<Typefood> getTypefoodArrayList() {
        return typefoodArrayList;
    }
}
