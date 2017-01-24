package boundary;

import entity.Pain;

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

@Path("/Pain")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class PainRepresentation {

    @EJB
    PainRessource pRessource;

    @GET
    public Response getAllPain(@Context UriInfo uriInfo){
        List<Pain> list_pain = this.pRessource.findAll();
        GenericEntity<List<Pain>> list = new GenericEntity<List<Pain>>(list_pain){
        };
        return Response.ok(list, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{painId}")
    public Response getPain(@PathParam("painId") String painId, @Context UriInfo uriInfo){
        Pain pain = this.pRessource.findById(painId);
        if(pain != null){
            return Response.ok(pain).build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response addPain(Pain pain, @Context UriInfo uriInfo){
        Pain newPain = this.pRessource.save(pain);
        URI uri = uriInfo.getAbsolutePathBuilder().path(newPain.getId()).build();
        return Response.created(uri)
                .entity(newPain)
                .build();
    }

    @DELETE
    @Path("/{painId}")
    public void deletePain(@PathParam("painId") String id){
        this.pRessource.delete(id);
    }
}
