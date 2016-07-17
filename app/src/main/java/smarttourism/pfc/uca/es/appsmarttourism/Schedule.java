package smarttourism.pfc.uca.es.appsmarttourism;

import java.io.Serializable;

/**
 * Created by AnaMari on 20/02/2016.
 */
public class Schedule implements Serializable {
    private String touristoffersId;
    private String date;
    private String schedule;

    public Schedule(String touristoffersId, String date, String schedule) {
        this.touristoffersId = touristoffersId;
        this.date = date;
        this.schedule = schedule;
    }

    /*public String getTouristoffersId() {
        return touristoffersId;
    }

    public void setTouristoffersId(String touristoffersId) {
        this.touristoffersId = touristoffersId;
    }*/

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSchedule() {
        return schedule;
    }

    /*public void setSchedule(String schedule) {
        this.schedule = schedule;
    }*/
}
