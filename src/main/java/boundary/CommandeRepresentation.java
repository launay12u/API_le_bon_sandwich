package boundary;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import entity.Sandwich;
import entity.Commande;
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

@Path("/commandes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
/**
 * Created by debian on 02/02/17.
 */
public class CommandeRepresentation {
    @EJB
    CommandeRessource cmdResource;

    @GET
    public Response getAllCommande(@Context UriInfo uriInfo){
        List<Commande> list_commande = this.cmdResource.findAll();
        GenericEntity<List<Commande>> list = new GenericEntity<List<Commande>>(list_commande) {
        };
        return Response.ok(list, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{commandeId}")
    public Response getCommande(@PathParam("commandeId") String commandeId, @Context UriInfo uriInfo) {
        Commande commande = this.cmdResource.findById(commandeId);
        if (commande != null) {
            return Response.ok(commande).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response addCommande(Commande commande, @Context UriInfo uriInfo) {
        Commande newCommande = this.cmdResource.save(commande);
        URI uri = uriInfo.getAbsolutePathBuilder().path(newCommande.getId()).build();
        return Response.created(uri)
                .entity(newCommande)
                .build();
    }

    @DELETE
    @Path("/{commandeId}")
    public void deleteCommande(@PathParam("commandeId") String id) {
        this.cmdResource.delete(id);
    }
}
