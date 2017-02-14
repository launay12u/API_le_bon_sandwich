package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
@NamedQuery(name = "CategorieIngredient.FindAll",query = "SELECT c FROM CategorieIngredient c")
public class CategorieIngredient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String nom;
    private String description;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "categorie")
    @JsonManagedReference
    private List<Ingredient> ingredients = new ArrayList<Ingredient>();

    @XmlElement(name="_links")
    @Transient
    private List<Link> links = new ArrayList<>();

    public CategorieIngredient(){

    }

    public CategorieIngredient(String n, String d){
        this.nom = n;
        this.description = d;
    }

    public CategorieIngredient(String n, String d, List<Ingredient> ing){
        this.nom =n;
        this.description=d;
        this.ingredients = ing;
    }

    public void addLink(String rel, String uri){
        this.links.add(new Link(rel, uri));
    }

    public String getNom() {
        return nom;
    }

    public String getId(){return this.id;}

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setId(String id){this.id = id;}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Link> getLinks() {
        return links;
    }
}
