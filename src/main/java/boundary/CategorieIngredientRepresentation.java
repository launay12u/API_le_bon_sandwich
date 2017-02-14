package boundary;

import com.sun.jndi.toolkit.url.Uri;
import entity.CategorieIngredient;
import entity.Ingredient;
import provider.Secured;

import java.net.URI;
import java.util.ArrayList;
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
    @EJB
    IngredientRessource ingResource;

    @GET
    public Response getAllCategorieIngredient(@Context UriInfo uriInfo){
        List<CategorieIngredient> list_categorie = this.ciResource.findAll();
        if(list_categorie != null) {
            for(CategorieIngredient ci : list_categorie) {
                List<Ingredient> l_ing = this.ingResource.findAll(ci.getId());
                ci.getLinks().clear();
                ci.addLink(this.getUriForSelfCategorie(uriInfo, ci), "self");
                ci.addLink(this.getUriForIngredients(uriInfo, ci), "ingredients");
                for(Ingredient ing : l_ing){
                    ing.getLinks().clear();
                    ing.addLink(this.getUriForSelfIngredient(uriInfo, ci, ing), "self");
                }
                ci.setIngredients(l_ing);
            }
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
            categorie.getLinks().clear();
            categorie.addLink(this.getUriForSelfCategorie(uriInfo, categorie), "self");
            categorie.addLink(this.getUriForIngredients(uriInfo, categorie), "ingredients");
            List<Ingredient> l_ing = this.ingResource.findAll(categorie.getId());
            categorie.setIngredients(l_ing);
            return Response.ok(categorie).build();
        }else{
            JsonObject jsonError = Json.createObjectBuilder().
                    add("error", "Cette catégorie n'existe pas.").build();
            return Response.status(Response.Status.NOT_FOUND).entity(jsonError).build();
        }
    }

    @Secured
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
    @Path("/{caegorieId}/ingredient")
    public Response getIngredients(@PathParam("caegorieId") String categorieId){
        CategorieIngredient ci = this.ciResource.findById(categorieId);
        if (ci != null) {
            List<Ingredient> l_ing = this.ingResource.findAll(categorieId);
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

    @GET
    @Path("{categorieId}/ingredients/{ingredientId}")
    public Response getOneIngredient(@PathParam("categorieId") String categorieId, @PathParam("ingredientId") String ingredientId, @Context UriInfo uriInfo){
        Ingredient ing = this.ingResource.findById(ingredientId);
        return Response.ok(ing, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/{categorieId}/ingredients")
    public Response addIngredient(@PathParam("categorieId") String categorieId, Ingredient ingredient, @Context UriInfo uriInfo){
        Ingredient ing = this.ingResource.ajouteIngredient(categorieId, ingredient);
        URI uri = uriInfo.getAbsolutePathBuilder()
                .path("/")
                .path(ing.getId())
                .build();
        return Response.created(uri).entity(ing).build();
    }

    @DELETE
    @Path("/{categorieId}")
    public void deleteCategorie(@PathParam("categorieId") String id){
        this.ciResource.delete(id);
    }

    private String getUriForSelfCategorie(UriInfo uriInfo, CategorieIngredient categorie){
        String uri = uriInfo.getBaseUriBuilder()
                .path(CategorieIngredientRepresentation.class)
                .path(categorie.getId())
                .build().toString();
        return uri;
    }

    private String getUriForCategorie(UriInfo uriInfo){
        String uri = uriInfo.getBaseUriBuilder()
                .path(CategorieIngredientRepresentation.class)
                .build().toString();
        return uri;
    }

    private String getUriForSelfIngredient(UriInfo uriInfo, CategorieIngredient categorie, Ingredient ing){
        String uri = uriInfo.getBaseUriBuilder()
                .path(CategorieIngredientRepresentation.class)
                .path(ing.getId())
                .path(IngredientRepresentation.class)
                .path(categorie.getId())
                .build().toString();
        return uri;
    }

    private String getUriForIngredients(UriInfo uriInfo, CategorieIngredient categorie){
        String uri = uriInfo.getBaseUriBuilder()
                .path(CategorieIngredientRepresentation.class)
                .path(categorie.getId())
                .path(IngredientRepresentation.class)
                .build().toString();
        return uri;
    }

}
