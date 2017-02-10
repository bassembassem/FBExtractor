package etu.bassem.facebook;

public class Pharmacie extends Object {

    private int id;
    private String nom;
    private String adresse;
    private Double lat;
    private Double lng;

    public Pharmacie(int id, String nom, String adresse, Double lat, Double lng) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.lat = lat;
        this.lng = lng;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }
    
    
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return nom;
    }
}
