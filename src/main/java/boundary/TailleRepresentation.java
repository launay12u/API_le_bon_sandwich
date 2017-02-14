package boundary;

import entity.Taille;
import provider.Secured;

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
        if (list_taille != null) {
            GenericEntity<List<Taille>> list = new GenericEntity<List<Taille>>(list_taille) {
            };
            return Response.ok(list, MediaType.APPLICATION_JSON).build();
        }else{
            JsonObject jsonError = Json.createObjectBuilder().
                    add("error", "Aucune taille crée.").build();
            return Response.status(Response.Status.NOT_FOUND).entity(jsonError).build();
        }
    }

    @GET
    @Path("/{tailleId}")
    public Response getTaille(@PathParam("tailleId") String tailleId, @Context UriInfo uriInfo){
        Taille taille = this.tResource.findById(tailleId);
        if(taille != null){
            return Response.ok(taille).build();
        }else{
            JsonObject jsonError = Json.createObjectBuilder().
                    add("error", "Cette taille n'existe pas.").build();
            return Response.status(Response.Status.NOT_FOUND).entity(jsonError).build();
        }
    }

    @Secured
    @POST
    public Response addTaille(Taille taille, @Context UriInfo uriInfo){
        if (taille.getNom() != null) {
            Taille newTaille = this.tResource.save(taille);
            URI uri = uriInfo.getAbsolutePathBuilder().path(newTaille.getId()).build();
            return Response.created(uri)
                    .entity(newTaille)
                    .build();
        }else{
            JsonObject jsonError = Json.createObjectBuilder().
                    add("error", "Aucune taille crée.").build();
            return Response.status(Response.Status.NOT_FOUND).entity(jsonError).build();
        }
    }

    @DELETE
    @Path("/{tailleId}")
    public void deletePain(@PathParam("tailleId") String id){
        this.tResource.delete(id);
    }
}
