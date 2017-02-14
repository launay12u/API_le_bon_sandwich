package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
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
    @JsonBackReference
    private CategorieIngredient categorie = null;

    @XmlElement(name="_links")
    @Transient
    private List<Link> links = new ArrayList<>();

    public Ingredient(){}

    public Ingredient(String n){
        this.nom = n;
    }

    public Ingredient(String n, CategorieIngredient cat){
        this.nom = n;
        this.categorie = cat;
    }

    public void addLink(String rel, String uri){
        this.links.add(new Link(rel, uri));
    }

    public String getNom() {
        return nom;
    }

    public String getId(){return id;}

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

    public List<Link> getLinks() {
        return links;
    }
}
