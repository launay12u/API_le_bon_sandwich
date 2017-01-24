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
@NamedQuery(name = "Pain.FindAll",query = "SELECT p FROM Pain p")
public class Pain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String type;

    public Pain(){}

    public Pain(String t){
        this.type = t;
    }

    public String getType() {
        return type;
    }

    public String getId(){return this.id;}

    public void setType(String type) {
        this.type = type;
    }

    public void setId(String id){ this.id = id;}
}
