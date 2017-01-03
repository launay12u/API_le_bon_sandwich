package entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

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

    public CategorieIngredient(){

    }

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
