package boundary;

import entity.Taille;
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

@Path("/taille")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class TailleRepresentation {

    @EJB
    TailleRessource tResource;

    @GET
    public Response getAllTaille(@Context UriInfo uriInfo){
    List<Taille> list_taille = this.tResource.findAll();
    GenericEntity<List<Taille>> list = new GenericEntity<List<Taille>>(list_taille){};
    return Response.ok(list, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{tailleId}")
    public Response getTaille(@PathParam("tailleId") String tailleId, @Context UriInfo uriInfo){
        Taille taille = this.tResource.findById(tailleId);
        if(taille != null){
            return Response.ok(taille).build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response addTaille(Taille taille, @Context UriInfo uriInfo){
        Taille newTaille = this.tResource.save(taille);
        URI uri = uriInfo.getAbsolutePathBuilder().path(newTaille.getId()).build();
        return Response.created(uri)
                .entity(newTaille)
                .build();
    }

    @DELETE
    @Path("/{tailleId}")
    public void deletePain(@PathParam("tailleId") String id){
        this.tResource.delete(id);
    }
}
