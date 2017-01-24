package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by debian on 03/01/17.
 */

@Entity
@XmlRootElement
@NamedQuery(name = "Ingredient.FindAll",query = "SELECT i FROM Ingredient i")
public class Ingredient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String nom;
    @ManyToOne
    private CategorieIngredient categorie;
    @ManyToOne
    @JsonBackReference
    private Sandwich sandwich;

    public Ingredient(){}

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

    public void setId(String id){this.id = id;}

    public CategorieIngredient getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieIngredient categorie) {
        this.categorie = categorie;
    }
}
