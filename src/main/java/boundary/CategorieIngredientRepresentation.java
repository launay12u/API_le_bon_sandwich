package boundary;

import com.sun.jndi.toolkit.url.Uri;
import entity.CategorieIngredient;
import entity.Ingredient;

import java.net.URI;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.*;

/**
 * Created by debian on 24/01/17.
 */

@Path("/categorieIngredient")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless

public class CategorieIngredientRepresentation
{
    @EJB
    CategorieIngredientRessource ciResource;

    @GET
    public Response getAllCategorieIngredient(@Context UriInfo uriInfo){
        List<CategorieIngredient> list_categorie = this.ciResource.findAll();
        if(list_categorie != null) {
            GenericEntity<List<CategorieIngredient>> list = new GenericEntity<List<CategorieIngredient>>(list_categorie) {
            };
            return Response.ok(list, MediaType.APPLICATION_JSON).build();
        }else{
            JsonObject jsonError = Json.createObjectBuilder().
                    add("error", "Aucune catégorie crée.").build();
            return Response.status(Response.Status.NOT_FOUND).entity(jsonError).build();
        }
    }

    @GET
    @Path("/{categorieId}")
    public Response getCategorie(@PathParam("categorieId") String id, @Context UriInfo uriInfo){
        CategorieIngredient categorie = this.ciResource.findById(id);
        if(categorie != null){
            return Response.ok(categorie).build();
        }else{
            JsonObject jsonError = Json.createObjectBuilder().
                    add("error", "Cette catégorie n'existe pas.").build();
            return Response.status(Response.Status.NOT_FOUND).entity(jsonError).build();
        }
    }

    @POST
    public Response addCategorie(CategorieIngredient categorie, @Context UriInfo uriInfo){
        if(categorie.getNom() != null) {
            if (categorie.getDescription() != null) {
                CategorieIngredient newCategorie = this.ciResource.save(categorie);
                URI uri = uriInfo.getAbsolutePathBuilder().path(newCategorie.getId()).build();
                return Response.created(uri)
                        .entity(newCategorie)
                        .build();
            }else {
                JsonObject jsonError = Json.createObjectBuilder().
                        add("error", "Il manque le paramètre description.").build();
                return Response.status(Response.Status.NOT_FOUND).entity(jsonError).build();
            }
        }else {
            JsonObject jsonError = Json.createObjectBuilder().
                    add("error", "Il manque le paramètre nom.").build();
            return Response.status(Response.Status.NOT_FOUND).entity(jsonError).build();
        }
    }

    @GET
    @Path("/{caegorieId}/ingredients")
    public Response getIngredients(@PathParam("caegorieId") String categorieId){
        CategorieIngredient ci = this.ciResource.findById(categorieId);
        if (ci != null) {
            List<Ingredient> l_ing = ci.getIngredients();
            if (l_ing != null) {
                GenericEntity<List<Ingredient>> list = new GenericEntity<List<Ingredient>>(l_ing) {
                };
                return Response.ok(list, MediaType.APPLICATION_JSON).build();
            }else{
                JsonObject jsonError = Json.createObjectBuilder().
                        add("error", "Liste d'ingrédients de cette catégorie non trouvé.").build();
                return Response.status(Response.Status.NOT_FOUND).entity(jsonError).build();
            }
        }else{
            JsonObject jsonError = Json.createObjectBuilder().
                    add("error", "Catégorie inexistante.").build();
            return Response.status(Response.Status.NOT_FOUND).entity(jsonError).build();
        }
    }

    @DELETE
    @Path("/{categorieId}")
    public void deleteCategorie(@PathParam("categorieId") String id){
        this.ciResource.delete(id);
    }

}
