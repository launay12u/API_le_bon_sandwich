package boundary;

import entity.Taille;

import java.util.List;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by debian on 24/01/17.
 */

@Stateless
public class TailleRessource {

    @PersistenceContext
    EntityManager em;

    public Taille findById(String id){
        return this.em.find(Taille.class, id);
    }

    public List<Taille> findAll(){
        Query q = this.em.createNamedQuery("Taille.FindAll", Taille.class);
        q.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
        return q.getResultList();
    }

    public Taille save(Taille t){
        t.setId(UUID.randomUUID().toString());
        return this.em.merge(t);
    }

    public void delete(String id){
        try{
            Taille ref = this.em.getReference(Taille.class, id);
            this.em.remove(ref);
        }catch(EntityNotFoundException e){

        }
    }
}
