package boundary;

import entity.Ingredient;
import entity.Pain;
import entity.Sandwich;
import entity.Taille;
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

    public Sandwich ajouteTaille(Sandwich sdw, Taille t){
        sdw.setTaille(t);
        this.em.persist(sdw);
        return sdw;
    }

    public Sandwich ajoutePain(Sandwich sdw, Pain p){
        sdw.setPain(p);
        this.em.persist(sdw);
        return sdw;
    }

    public Sandwich ajouteIngredients(Sandwich sdw, Ingredient ing){
        List<Ingredient> l_ing = sdw.getIngredients();
        l_ing.add(ing);
        sdw.setIngredients(l_ing);
        this.em.persist(sdw);
        return sdw;
    }

    public Sandwich update(Sandwich s, Sandwich updateSandwich){
        Sandwich sandwich = this.em.getReference(Sandwich.class, s.getId());
        sandwich.setIngredients(updateSandwich.getIngredients());
        sandwich.setNom(updateSandwich.getNom());
        sandwich.setPain(updateSandwich.getPain());
        sandwich.setTaille(updateSandwich.getTaille());
        return this.em.merge(sandwich);
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
