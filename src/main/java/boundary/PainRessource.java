package boundary;

import entity.Pain;

import java.util.List;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.persistence.*;

/**
 * Created by debian on 24/01/17.
 */

@Stateless
public class PainRessource {

    @PersistenceContext
    EntityManager em;

    public Pain findById(String id){
        return this.em.find(Pain.class, id);
    }

    public List<Pain> findAll(){
        Query q = this.em.createNamedQuery("Pain.FindAll", Pain.class);
        q.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
        return q.getResultList();
    }

    public Pain save(Pain p){
        p.setId(UUID.randomUUID().toString());
        return this.em.merge(p);
    }

    public void delete(String id){
        try{
            Pain ref = this.em.getReference(Pain.class, id);
            this.em.remove(ref);
        }catch (EntityNotFoundException e){

        }
    }
}
