package smarttourism.pfc.uca.es.appsmarttourism;

import java.io.Serializable;
import java.util.List;

/**
 * Created by AnaMari on 20/02/2016.
 */
public class ScheduleList implements Serializable {
    private List<Schedule> scheduleArrayList;

    public ScheduleList(){}

    public ScheduleList(List<Schedule> scheduleArrayList) {
        this.scheduleArrayList = scheduleArrayList;
    }

    public List<Schedule> getScheduleArrayList() {
        return scheduleArrayList;
    }

    public void setScheduleArrayList(List<Schedule> scheduleArrayList) {
        this.scheduleArrayList = scheduleArrayList;
    }
}
