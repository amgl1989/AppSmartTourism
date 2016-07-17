package smarttourism.pfc.uca.es.appsmarttourism;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AnaMari on 06/02/2016.
 */
public class TouristOffers{
    private String id;
    private String name;
    private double latitude;
    private double longitude;
    private String description;
    private Byte guided;
    private Byte booking;
    private Byte openAir;
    private List<TypeTourism> typeTourismList = new ArrayList<TypeTourism>();
    private List<Schedule> scheduleList = new ArrayList<Schedule>();

    public TouristOffers() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Byte getGuided() {
        return guided;
    }

    public void setGuided(Byte guided) {
        this.guided = guided;
    }

    public Byte getBooking() {
        return booking;
    }

    public void setBooking(Byte booking) {
        this.booking = booking;
    }

    public Byte getOpenAir() {
        return openAir;
    }

    public void setOpenAir(Byte openAir) {
        this.openAir = openAir;
    }

    public List<TypeTourism> getTypeTourismList() {
        return typeTourismList;
    }

    public void setTypeTourismList(List<TypeTourism> typeTourismList) {
        this.typeTourismList = typeTourismList;
    }

    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    /*@Override
    public String toString() {
        return "TouristOffers{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", description='" + description + '\'' +
                ", guided=" + guided +
                ", booking=" + booking +
                ", openAir=" + openAir +
                '}';
    }*/
}
