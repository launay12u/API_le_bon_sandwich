package boundary;

import entity.Sandwich;
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

@Path("/sandwichs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless

public class SandwichRepresentation {
    @EJB
    SandwichResource sdwResource;

    @GET
    public Response getAllSandwich(@Context UriInfo uriInfo){
        List<Sandwich> list_sandwich = this.sdwResource.findAll();
        GenericEntity<List<Sandwich>> list = new GenericEntity<List<Sandwich>>(list_sandwich) {
        };
        return Response.ok(list, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{sandwichId}")
    public Response getMessage(@PathParam("sandwichId") String sandwichId, @Context UriInfo uriInfo) {
        Sandwich sandwich = this.sdwResource.findById(sandwichId);
        if (sandwich != null) {
            return Response.ok(sandwich).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response addSandwich(Sandwich sandwich, @Context UriInfo uriInfo) {
        Sandwich newSandwich = this.sdwResource.save(sandwich);
        URI uri = uriInfo.getAbsolutePathBuilder().path(newSandwich.getId()).build();
        return Response.created(uri)
                .entity(newSandwich)
                .build();
    }

    @DELETE
    @Path("/{sandwichId}")
    public void deleteMessage(@PathParam("sandwichId") String id) {
        this.sdwResource.delete(id);
    }
}
