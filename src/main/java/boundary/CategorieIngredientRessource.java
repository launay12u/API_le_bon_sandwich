package boundary;

import entity.CategorieIngredient;

import java.util.List;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.enterprise.inject.Stereotype;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by debian on 24/01/17.
 */

@Stateless
public class CategorieIngredientRessource {

    @PersistenceContext
    EntityManager em;

    public CategorieIngredient findById(String id){
        return this.em.find(CategorieIngredient.class, id);
    }

    public List<CategorieIngredient> findAll(){
        Query q = this.em.createNamedQuery("CategorieIngredient.FindAll", CategorieIngredient.class);
        q.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
        return q.getResultList();

    }

    public CategorieIngredient save(CategorieIngredient ci){
        ci.setId(UUID.randomUUID().toString());
        return this.em.merge(ci);
    }

    public void delete(String id){
        try{
            CategorieIngredient ref = this.em.getReference(CategorieIngredient.class, id);
            this.em.remove(ref);
        }catch(EntityNotFoundException e){

        }
    }
}
