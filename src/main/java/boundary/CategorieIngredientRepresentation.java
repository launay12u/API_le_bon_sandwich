package boundary;

import com.sun.jndi.toolkit.url.Uri;
import entity.CategorieIngredient;
import java.net.URI;
import java.util.List;
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
        GenericEntity<List<CategorieIngredient>> list = new GenericEntity<List<CategorieIngredient>>(list_categorie){};
        return Response.ok(list, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{categorieId}")
    public Response getCategorie(@PathParam("categorieId") String id, @Context UriInfo uriInfo){
        CategorieIngredient categorie = this.ciResource.findById(id);
        if(categorie != null){
            return Response.ok(categorie).build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response addCategorie(CategorieIngredient categorie, @Context UriInfo uriInfo){
        CategorieIngredient newCategorie = this.ciResource.save(categorie);
        URI uri = uriInfo.getAbsolutePathBuilder().path(newCategorie.getId()).build();
        return Response.created(uri)
                .entity(newCategorie)
                .build();
    }

    @DELETE
    @Path("/{categorieId}")
    public void deleteCategorie(@PathParam("categorieId") String id){
        this.ciResource.delete(id);
    }

}
