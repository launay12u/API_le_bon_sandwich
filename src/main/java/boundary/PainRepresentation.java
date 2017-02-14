package boundary;

import entity.Pain;
import provider.Secured;

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

@Path("/pain")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class PainRepresentation {

    @EJB
    PainRessource pRessource;

    @GET
    public Response getAllPain(@Context UriInfo uriInfo){
        List<Pain> list_pain = this.pRessource.findAll();
        if (list_pain != null) {
            GenericEntity<List<Pain>> list = new GenericEntity<List<Pain>>(list_pain) {
            };
            return Response.ok(list, MediaType.APPLICATION_JSON).build();
        }else{
            JsonObject jsonError = Json.createObjectBuilder().
                    add("error", "Aucun pain crée.").build();
            return Response.status(Response.Status.NOT_FOUND).entity(jsonError).build();
        }
    }

    @GET
    @Path("/{painId}")
    public Response getPain(@PathParam("painId") String painId, @Context UriInfo uriInfo){
        Pain pain = this.pRessource.findById(painId);
        if(pain != null){
            return Response.ok(pain).build();
        }else{
            JsonObject jsonError = Json.createObjectBuilder().
                    add("error", "Aucun pain correspondant.").build();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Secured
    @POST
    //@Consumes(MediaType.APPLICATION_JSON)
    public Response addPain(Pain pain, @Context UriInfo uriInfo){
        if(pain.getType() != null) {
            Pain newPain = this.pRessource.save(pain);
            URI uri = uriInfo.getAbsolutePathBuilder().path(newPain.getId()).build();
            return Response.created(uri)
                    .entity(newPain)
                    .build();
        }else{
            JsonObject jsonError = Json.createObjectBuilder().
                    add("error", "Il manque le paramètre type.").build();
            return Response.status(Response.Status.NOT_FOUND).entity(jsonError).build();
        }
    }

    @DELETE
    @Path("/{painId}")
    public void deletePain(@PathParam("painId") String id){
        this.pRessource.delete(id);
    }
}
