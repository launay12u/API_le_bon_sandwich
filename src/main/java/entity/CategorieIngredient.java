package entity;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by debian on 03/01/17.
 */
@Entity
@XmlRootElement
@NamedQuery(name = "Categorie.FindAll",query = "SELECT c FROM Categorie c")
public class CategorieIngredient {

    private String nom;

    public CategorieIngredient(String n){
        this.nom = n;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
