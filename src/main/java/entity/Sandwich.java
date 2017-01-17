package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@NamedQuery(name = "Sandwich.FindAll",query = "SELECT s FROM Sandwich s")
public class Sandwich implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String nom;
    @ManyToOne
    @JsonBackReference
    private Taille taille;
    @ManyToOne
    @JsonBackReference
    private Pain pain;
    @OneToMany(mappedBy = "sandwich")
    @JsonBackReference
    private List<Ingredient> ingredients;
    @OneToOne
    private Commande commande;

    public Sandwich() {}

    public Sandwich(String id, Taille taille, Pain pain) {
        this.id = id;
        this.taille = taille;
        this.pain = pain;
        this.ingredients = new ArrayList<Ingredient>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Taille getTaille() {
        return taille;
    }

    public void setTaille(Taille taille) {
        this.taille = taille;
    }

    public Pain getPain() {
        return pain;
    }

    public void setPain(Pain pain) {
        this.pain = pain;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
