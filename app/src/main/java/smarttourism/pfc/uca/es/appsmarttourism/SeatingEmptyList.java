package smarttourism.pfc.uca.es.appsmarttourism;

import java.io.Serializable;
import java.util.List;

/**
 * Created by AnaMari on 19/02/2016.
 */
public class SeatingEmptyList implements Serializable {
    private List<SeatingEmpty> seatingEmptyArrayList;

    public SeatingEmptyList(){}

    public SeatingEmptyList(List<SeatingEmpty> seatingEmptyArrayList) {
        this.seatingEmptyArrayList = seatingEmptyArrayList;
    }

    public List<SeatingEmpty> getSeatingEmptyArrayList() {
        return seatingEmptyArrayList;
    }

    public void setSeatingEmptyArrayList(List<SeatingEmpty> seatingEmptyArrayList) {
        this.seatingEmptyArrayList = seatingEmptyArrayList;
    }
}
