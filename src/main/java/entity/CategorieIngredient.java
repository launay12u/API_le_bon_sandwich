package entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categorie")
    @JsonManagedReference
    private List<Ingredient> ingredients;

    public CategorieIngredient(){

    }

    public CategorieIngredient(String n){
        this.nom = n;
    }

    public String getNom() {
        return nom;
    }

    public String getId(){return this.id;}

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setId(String id){this.id = id;}
}
