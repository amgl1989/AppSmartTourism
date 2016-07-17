package smarttourism.pfc.uca.es.appsmarttourism;

import java.io.Serializable;

/**
 * Created by AnaMari on 19/02/2016.
 */
public class SeatingEmpty implements Serializable{
    private String restaurantId;
    private String date;
    private String seatingEmpty;

   // public SeatingEmpty(){}

    public SeatingEmpty(String restaurantId, String date, String seatingEmpty) {
        this.restaurantId = restaurantId;
        this.date = date;
        this.seatingEmpty = seatingEmpty;
    }
/*
    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }*/

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSeatingEmpty() {
        return seatingEmpty;
    }

   /* public void setSeatingEmpty(String seatingEmpty) {
        this.seatingEmpty = seatingEmpty;
    }*/
}
