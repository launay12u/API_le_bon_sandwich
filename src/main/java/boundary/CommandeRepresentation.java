package boundary;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.Entity;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import entity.Sandwich;
import entity.Commande;
import java.net.URI;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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

    @EJB
    SandwichResource snwRessource;

    @GET
    public Response getAllCommande(@Context UriInfo uriInfo){
        List<Commande> list_commande = this.cmdResource.findAll();
        if (list_commande != null) {
            GenericEntity<List<Commande>> list = new GenericEntity<List<Commande>>(list_commande) {
            };
            return Response.ok(list, MediaType.APPLICATION_JSON).build();
        }else{
            JsonObject jsonError = Json.createObjectBuilder().
                    add("error", "Aucune commande existante.").build();
            return Response.status(Response.Status.NOT_FOUND).entity(jsonError).build();
        }
    }

    @GET
    @Path("/{commandeId}")
    public Response getCommande(@PathParam("commandeId") String commandeId, @Context UriInfo uriInfo) {
        Commande commande = this.cmdResource.findById(commandeId);
        if (commande != null) {
            return Response.ok(commande).build();
        } else {
            JsonObject jsonError = Json.createObjectBuilder().
                    add("error", "Cette commande n'existe pas.").build();
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

    @POST
    @Path("/{commandeToken}/{commandeId}/addSandwich")
    public Response addSandwichToCommande(@PathParam("commandeToken") String commandeToken, @PathParam("commandeId") String commandeId, Sandwich sandwich, @Context UriInfo uriInfo){
        Commande commande = this.cmdResource.findById(commandeId);
        if (commande.getToken() == commandeToken) {
            URI uri = uriInfo.getAbsolutePathBuilder().path(commande.getId()).build();
            return Response.created(uri)
                    .entity(commande)
                    .build();
        }else{
            JsonObject jsonError = Json.createObjectBuilder().
                    add("error", "Le token ne correspond pas.").build();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/{commandeToken}/{commandeId}/validate")
    public Response validateCommande(@PathParam("commandeToken") String commandeToken, @PathParam("commandeId") String commandeId){
        Commande commande = this.cmdResource.findById(commandeId);
        if(commande.getToken() == commandeToken){
            this.cmdResource.update(commandeId, "validated");
            JsonObject jsonSuccess = Json.createObjectBuilder().
                    add("success", "La commande a été modifiée.").build();
            return Response.status(Response.Status.OK).build();
        }else{
            JsonObject jsonError = Json.createObjectBuilder().
                    add("error", "Le token ne correspond pas.").build();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{commandeToken}/{commandeId}/update/{sandwichId}")
    public Response updateSandwich(@PathParam("commandeToken") String commandeToken, @PathParam("commandeId") String commandeId, @PathParam("sandwichId") String sandwichId, Sandwich s){
        Commande commande = this.cmdResource.findById(commandeId);
        if(commande.getToken() == commandeToken && commande.getEtat() != "validated"){
            Sandwich sandwich = this.snwRessource.findById(sandwichId);
            this.snwRessource.update(sandwich, s);
            JsonObject jsonSuccess = Json.createObjectBuilder().
                    add("success", "Le sandwich a été modifié.").build();
            return Response.status(Response.Status.OK).build();
        }else{
            JsonObject jsonError = Json.createObjectBuilder().
                    add("error", "Le token ne correspond pas ou la commande est déjà validée.").build();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{commandeId}")
    public void deleteCommande(@PathParam("commandeId") String id) {
        this.cmdResource.delete(id);
    }
}
