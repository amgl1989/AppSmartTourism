package smarttourism.pfc.uca.es.appsmarttourism;

/**
 * Created by AnaMari on 18/02/2016.
 */

public class FreshFood implements java.io.Serializable {
    private String restaurantId;
    private String date;
    private String freshfood;

    public FreshFood(String restaurantId, String date, String freshfood) {
        this.restaurantId = restaurantId;
        this.date = date;
        this.freshfood = freshfood;
    }

    public String getDate() {
        return date;
    }

    public String getFreshfood() {
        return freshfood;
    }
}
