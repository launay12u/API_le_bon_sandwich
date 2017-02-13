package boundary;

import entity.Sandwich;
//import sun.plugin2.message.Message;
import entity.Commande;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
/**
 * Created by debian on 02/02/17.
 */
public class CommandeRessource {

    @PersistenceContext
    EntityManager em;


    public Commande findById(String id){
        return this.em.find(Commande.class,id);
    }

    public List<Commande> findAll() {
        Query q = this.em.createNamedQuery("Commande.FindAll", Commande.class);
        // pour éviter les pbs de cache
        q.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
        return q.getResultList();
    }

    public Commande save(Commande cmd) {
        cmd.setId(UUID.randomUUID().toString());
        return this.em.merge(cmd);
    }

    public Commande ajouteSandwich(Commande cmd, Sandwich sdw){
        List<Sandwich> l_sdw = cmd.getSandwichs();
        l_sdw.add(sdw);
        cmd.setSandwichs(l_sdw);
        this.em.persist(cmd);
        return cmd;
    }

    public void delete(String id) {
        try {
            Commande ref = this.em.getReference(Commande.class, id);
            this.em.remove(ref);
        } catch (EntityNotFoundException e) {
            // on veut supprimer, et elle n'existe pas, donc c'est bon
        }
    }
}
