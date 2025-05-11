package modelo;

import java.util.Date;

public class Plataforma {
    private int platformId;
    private Date dateAdded;
    private String platformName;

    // Constructor
    public Plataforma() { }

    public Plataforma(String platformName) {
        this.platformName = platformName;
    }

    public Plataforma(int platformId, Date dateAdded, String platformName) {
        this.platformId = platformId;
        this.dateAdded = dateAdded;
        this.platformName = platformName;
    }

    // Getters y Setters
    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    @Override
    public String toString() {
        return "Plataforma{" +
                "platformId=" + platformId +
                ", dateAdded=" + dateAdded +
                ", platformName='" + platformName + '\'' +
                '}';
    }
}