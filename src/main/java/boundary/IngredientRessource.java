package boundary;

import entity.CategorieIngredient;
import entity.Ingredient;
import entity.Sandwich;
//import sun.plugin2.message.Message;

import java.util.ArrayList;
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
public class IngredientRessource {

    @PersistenceContext
    EntityManager em;

    public Ingredient findById(String id){
        return this.em.find(Ingredient.class, id);
    }

    public List<Ingredient> findAll(){
        Query q = this.em.createNamedQuery("Ingredient.FindAll", Ingredient.class);
        q.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
        return q.getResultList();
    }

    public List<Ingredient> findAll(String categorieId){
        Query q = this.em.createQuery("SELECT i FROM Ingredient i where i.categorie.id= :id");
        q.setParameter("id", categorieId);
        //q.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
        return q.getResultList();
    }

    public Ingredient ajouteIngredient(String categId, Ingredient ing){
        Ingredient i = new Ingredient(ing.getNom());
        i.setId(UUID.randomUUID().toString());
        i.setCategorie(this.em.find(CategorieIngredient.class, categId));
        this.em.persist(i);
        return i;
    }

    public void delete(String id){
        try{
            Ingredient ref = this.em.getReference(Ingredient.class, id);
            this.em.remove(ref);
        }catch (EntityNotFoundException e){

        }
    }





}
