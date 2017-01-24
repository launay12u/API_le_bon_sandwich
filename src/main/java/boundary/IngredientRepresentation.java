package boundary;

import entity.Ingredient;
import entity.Sandwich;

import java.net.URI;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.ejb.Stateless;
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

@Path("/ingredients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless

public class IngredientRepresentation {

    @EJB
    IngredientRessource ingRessource;

    @GET
    public Response getAllIngredient(@Context UriInfo uriInfo){
        List<Ingredient> list_ingredient = this.ingRessource.findAll();
        GenericEntity<List<Ingredient>> list = new GenericEntity<List<Ingredient>>(list_ingredient){
        };
        return Response.ok(list, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{ingredientId}")
    public Response getIngredient(@PathParam("ingredientId") String ingredientId, @Context UriInfo uriInfo){
        Ingredient ingredient = this.ingRessource.findById(ingredientId);
        if(ingredient != null){
            return Response.ok(ingredient).build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response addIngredient(Ingredient ingredient, @Context UriInfo uriInfo){
        Ingredient newIngredient = this.ingRessource.save(ingredient);
        URI uri = uriInfo.getAbsolutePathBuilder().path(newIngredient.getId()).build();
        return Response.created(uri)
                .entity(newIngredient)
                .build();
    }

    @DELETE
    @Path("/{ingredientId}")
    public void deleteIngredient(@PathParam("ingredientId") String id){
        this.ingRessource.delete(id);
    }

}
