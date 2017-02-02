package entity;

/**
 * Created by debian on 02/02/17.
 */

        import com.fasterxml.jackson.annotation.JsonBackReference;

        import javax.persistence.*;
        import javax.xml.bind.annotation.XmlRootElement;
        import java.io.Serializable;
        import java.util.Date;
        import java.util.List;



@Entity
@XmlRootElement
public class Commande implements Serializable{

    @Id
    private String id;
    @OneToMany
    @JsonBackReference
    private List<Sandwich> sandwichs;
    private Date dateRetrait;
    private String Etat;

    public Commande(){}

    public List<Sandwich> getSandwichs() {
        return sandwichs;
    }

    public Date getDateRetrait() {
        return dateRetrait;
    }

    public void setDateRetrait(Date dateRetrait) {
        this.dateRetrait = dateRetrait;
    }

    public String getEtat() {
        return Etat;
    }

    public void setEtat(String etat) {
        Etat = etat;
    }
}