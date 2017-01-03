package entity;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by debian on 03/01/17.
 */

@Entity
@XmlRootElement
@NamedQuery(name = "Ingredient.FindAll",query = "SELECT i FROM Ingredient i")
public class Ingredient {

    private String nom;
    private CategorieIngredient categorie;

    public Ingredient(String n, CategorieIngredient cat){
        this.nom = n;
        this.categorie = cat;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public CategorieIngredient getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieIngredient categorie) {
        this.categorie = categorie;
    }
}
