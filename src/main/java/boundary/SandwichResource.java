package boundary;

import entity.Sandwich;
import sun.plugin2.message.Message;

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
public class SandwichResource {

    @PersistenceContext
    EntityManager em;


    public Sandwich findById(String id){
        return this.em.find(Sandwich.class,id);
    }

    public List<Sandwich> findAll() {
        Query q = this.em.createNamedQuery("Sandwich.FindAll", Sandwich.class);
        // pour éviter les pbs de cache
        q.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
        return q.getResultList();
    }

    public Sandwich save(Sandwich sdw) {
        sdw.setId(UUID.randomUUID().toString());
        return this.em.merge(sdw);
    }

    public void delete(String id) {
        try {
            Sandwich ref = this.em.getReference(Sandwich.class, id);
            this.em.remove(ref);
        } catch (EntityNotFoundException e) {
            // on veut supprimer, et elle n'existe pas, donc c'est bon
        }
    }
}
