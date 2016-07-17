package smarttourism.pfc.uca.es.appsmarttourism;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AnaMari on 18/03/2016.
 */
public class GeneralPreference {
    private String username;
    private byte weather;
    private byte openAir;
    private byte guided;
    private byte terrace;
    private byte booked;
    private byte freshFood;
    private byte cleaning;
    private byte schedule;
    private List<TypeRestaurantPreference> typeRestaurantPreferenceList = new ArrayList<TypeRestaurantPreference>();
    private List<TypeFoodPreference> typeFoodPreferenceList = new ArrayList<TypeFoodPreference>();
    private List<TypeTourismPreference> typeTourismPreferenceList = new ArrayList<TypeTourismPreference>();

    public GeneralPreference(){}

    public GeneralPreference(GeneralPreference generalPreference){
        username = generalPreference.username;
        weather = generalPreference.weather;
        openAir = generalPreference.openAir;
        guided = generalPreference.guided;
        terrace = generalPreference.terrace;
        booked = generalPreference.booked;
        freshFood = generalPreference.freshFood;
        cleaning = generalPreference.cleaning;
        schedule = generalPreference.schedule;
        typeRestaurantPreferenceList = generalPreference.typeRestaurantPreferenceList;
        typeFoodPreferenceList = generalPreference.typeFoodPreferenceList;
        typeTourismPreferenceList = generalPreference.typeTourismPreferenceList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte getOpenAir() {
        return openAir;
    }

    public void setOpenAir(byte openAir) {
        this.openAir = openAir;
    }

    public byte getGuided() {
        return guided;
    }

    public void setGuided(byte guided) {
        this.guided = guided;
    }

    public byte getTerrace() {
        return terrace;
    }

    public void setTerrace(byte terrace) {
        this.terrace = terrace;
    }

    public byte getBooked() {
        return booked;
    }

    public void setBooked(byte booked) {
        this.booked = booked;
    }

    public byte getFreshFood() {
        return freshFood;
    }

    public void setFreshFood(byte freshFood) {
        this.freshFood = freshFood;
    }

    public byte getCleaning() {
        return cleaning;
    }

    public void setCleaning(byte cleaning) {
        this.cleaning = cleaning;
    }

    public byte getSchedule() {
        return schedule;
    }

    public void setSchedule(byte schedule) {
        this.schedule = schedule;
    }

    public List<TypeRestaurantPreference> getTypeRestaurantPreferenceList() {
        return typeRestaurantPreferenceList;
    }

    public void setTypeRestaurantPreferenceList(List<TypeRestaurantPreference> typeRestaurantPreferenceList) {
        this.typeRestaurantPreferenceList = typeRestaurantPreferenceList;
    }

    public List<TypeFoodPreference> getTypeFoodPreferenceList() {
        return typeFoodPreferenceList;
    }

    public void setTypeFoodPreferenceList(List<TypeFoodPreference> typeFoodPreferenceList) {
        this.typeFoodPreferenceList = typeFoodPreferenceList;
    }

    public List<TypeTourismPreference> getTypeTourismPreferenceList() {
        return typeTourismPreferenceList;
    }

    public void setTypeTourismPreferenceList(List<TypeTourismPreference> typeTourismPreferenceList) {
        this.typeTourismPreferenceList = typeTourismPreferenceList;
    }
}
