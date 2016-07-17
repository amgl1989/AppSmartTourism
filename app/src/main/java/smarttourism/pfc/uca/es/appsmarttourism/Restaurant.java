package smarttourism.pfc.uca.es.appsmarttourism;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AnaMari on 23/01/2016.
 */
public class Restaurant implements Serializable{
        private String id;
        private String name;
        private double latitude;
        private double longitude;
        private String description;
        private Integer cleaning;
        private Integer numSeating;
        private Byte terrace;
        private List<Typerestaurant> typeRestList = new ArrayList<Typerestaurant>();
        private List<Typefood> typefoodList = new ArrayList<Typefood>();
        private List<FreshFood> freshfoodList = new ArrayList<FreshFood>();
        private List<SeatingEmpty> seatingEmptyList = new ArrayList<SeatingEmpty>();

        public Restaurant(){}

        public Restaurant(String id) {
            this.id = id;
        }

        public Restaurant(String id, String name, double latitude, double longitude, String description, Integer cleaning, Integer numSeating, Byte terrace) {
            this.id = id;
            this.name = name;
            this.latitude = latitude;
            this.longitude = longitude;
            this.description = description;
            this.cleaning = cleaning;
            this.numSeating = numSeating;
            this.terrace = terrace;
        }

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

        public Integer getCleaning() {
            return cleaning;
        }

        public void setCleaning(Integer cleaning) {
            this.cleaning = cleaning;
        }

        public Integer getNumSeating() {
            return numSeating;
        }

        public void setNumSeating(Integer numSeating) {
            this.numSeating = numSeating;
        }

        public Byte getTerrace() {
            return terrace;
        }

        public void setTerrace(Byte terrace) {
            this.terrace = terrace;
        }

        public List<Typerestaurant> getTypeRestList() {
            return typeRestList;
        }

        public void setTypeRestList(List<Typerestaurant> typeRestList) {
            this.typeRestList = typeRestList;
        }

        public List<Typefood> getTypefoodList() {return typefoodList; }

        public void setTypefoodList(List<Typefood> typefoodList) {
            this.typefoodList = typefoodList;
        }

        public List<FreshFood> getFreshfoodList() {
            return freshfoodList;
        }

        public void setFreshfoodList(List<FreshFood> freshfoodList) {
            this.freshfoodList = freshfoodList;
        }

        public List<SeatingEmpty> getSeatingEmptyList() {
            return seatingEmptyList;
        }

        public void setSeatingEmptyList(List<SeatingEmpty> seatingEmptyList) {
            this.seatingEmptyList = seatingEmptyList;
        }
}
