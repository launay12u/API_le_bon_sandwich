package entity;

/**
 * Created by debian on 02/02/17.
 */

        import com.fasterxml.jackson.annotation.JsonBackReference;
        import com.fasterxml.jackson.annotation.JsonManagedReference;

        import javax.persistence.*;
        import javax.xml.bind.annotation.XmlRootElement;
        import java.io.Serializable;
        import java.security.MessageDigest;
        import java.security.NoSuchAlgorithmException;
        import java.util.Date;
        import java.util.List;



@Entity
@XmlRootElement
@NamedQuery(name = "Commande.FindAll",query = "SELECT c FROM Commande c")
public class Commande implements Serializable{

    @Id
    private String id;

    @OneToMany
    @JsonManagedReference
    private List<Sandwich> sandwichs;
    private Date dateRetrait;
    private String Etat;

    private String token;

    public Commande(){
        try {
            this.token= MessageDigest.getInstance("MD5").digest(Long.toBinaryString(System.currentTimeMillis()).getBytes()).toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Sandwich> getSandwichs() {
        return sandwichs;
    }

    public void setSandwichs(List<Sandwich> sandwichs) {
        this.sandwichs = sandwichs;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}