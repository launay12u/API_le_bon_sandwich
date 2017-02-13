package boundary;

import entity.Ingredient;

import java.net.URI;
import java.util.List;
import java.util.ResourceBundle;
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

@Path("/ingredient")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless

public class IngredientRepresentation {

    @EJB
    IngredientRessource ingRessource;

    @GET
    public Response getAllIngredient(@Context UriInfo uriInfo){
        List<Ingredient> list_ingredient = this.ingRessource.findAll();
        if (list_ingredient != null) {
            GenericEntity<List<Ingredient>> list = new GenericEntity<List<Ingredient>>(list_ingredient) {
            };
            return Response.ok(list, MediaType.APPLICATION_JSON).build();
        }else{
            JsonObject jsonError = Json.createObjectBuilder().
                    add("error", "Aucun ingrédient crée.").build();
            return Response.status(Response.Status.NOT_FOUND).entity(jsonError).build();
        }
    }

    @GET
    @Path("/{ingredientId}")
    public Response getIngredient(@PathParam("ingredientId") String ingredientId, @Context UriInfo uriInfo){
        Ingredient ingredient = this.ingRessource.findById(ingredientId);
        if(ingredient != null){
            return Response.ok(ingredient).build();
        }else{
            JsonObject jsonError = Json.createObjectBuilder().
                    add("error", "Cette ingrédient n'existe pas.").build();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response addIngredient(Ingredient ingredient, @Context UriInfo uriInfo){
        if (ingredient.getNom() != null) {
            Ingredient newIngredient = this.ingRessource.save(ingredient);
            URI uri = uriInfo.getAbsolutePathBuilder().path(newIngredient.getId()).build();
            return Response.created(uri)
                    .entity(newIngredient)
                    .build();
        }else{
            JsonObject jsonError = Json.createObjectBuilder().
                    add("error", "Il manque le paramètre nom.").build();
            return Response.status(Response.Status.NOT_FOUND).entity(jsonError).build();
        }
    }

    @DELETE
    @Path("/{ingredientId}")
    public void deleteIngredient(@PathParam("ingredientId") String id){
        this.ingRessource.delete(id);
    }

}
