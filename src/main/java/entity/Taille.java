package entity;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by debian on 03/01/17.
 */

@Entity
@XmlRootElement
@NamedQuery(name = "Taille.FindAll",query = "SELECT t FROM Taille t")
public class Taille {

    private String nom;
    private int nb_ingredient;
    private double prix;

    public Taille(String n, int nb, double px){
        this.nom = n;
        this.nb_ingredient = nb;
        this.prix = px;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNb_ingredient() {
        return nb_ingredient;
    }

    public void setNb_ingredient(int nb_ingredient) {
        this.nb_ingredient = nb_ingredient;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
}
