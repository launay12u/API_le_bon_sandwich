package entity;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by debian on 31/01/17.
 */

@XmlRootElement
public class categorie_link_ingredient{

    private String id_categ;
    private String nom_categ;

    public categorie_link_ingredient(){}

    public categorie_link_ingredient(String id_categ, String nom_categ) {
        this.id_categ = id_categ;
        this.nom_categ = nom_categ;
    }

    public String getId_categ() {
        return id_categ;
    }

    public void setId_categ(String id_categ) {
        this.id_categ = id_categ;
    }

    public String getNom_categ() {
        return nom_categ;
    }

    public void setNom_categ(String nom_categ) {
        this.nom_categ = nom_categ;
    }
}
