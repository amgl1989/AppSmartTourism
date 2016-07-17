package smarttourism.pfc.uca.es.appsmarttourism;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by AnaMari on 28/02/2016.
 */
public class User implements Serializable {
    private String username;
    private String password;
    private String seed;
    private String name;
    private String surname;
    private Date dateBorn;
    private int age;
    private String countrySource;


    public User() { }

    public User (User user){
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.seed = user.getSeed();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.dateBorn = user.getDateBorn();
        this.age = user.getAge();
        this.countrySource = user.getCountrySource();
    }

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDateBorn() {
        return dateBorn;
    }

    public void setDateBorn(Date dateBorn) {
        this.dateBorn = dateBorn;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCountrySource() {
        return countrySource;
    }

    public void setCountrySource(String countrySource) {
        this.countrySource = countrySource;
    }
}
