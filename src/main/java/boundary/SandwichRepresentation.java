package boundary;

import entity.Ingredient;
import entity.Pain;
import entity.Sandwich;
import entity.Taille;

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

@Path("/sandwichs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless

public class SandwichRepresentation {
    @EJB
    SandwichResource sdwResource;

    @EJB
    IngredientRessource ingResource;

    @GET
    public Response getAllSandwich(@Context UriInfo uriInfo){
        List<Sandwich> list_sandwich = this.sdwResource.findAll();
        if (list_sandwich != null) {
            GenericEntity<List<Sandwich>> list = new GenericEntity<List<Sandwich>>(list_sandwich) {
            };
            return Response.ok(list, MediaType.APPLICATION_JSON).build();
        }else{
            JsonObject jsonError = Json.createObjectBuilder().
                    add("error", "Aucun sandwich crée.").build();
            return Response.status(Response.Status.NOT_FOUND).entity(jsonError).build();
        }
    }

    @GET
    @Path("/{sandwichId}")
    public Response getSandwich(@PathParam("sandwichId") String sandwichId, @Context UriInfo uriInfo) {
        Sandwich sandwich = this.sdwResource.findById(sandwichId);
        if (sandwich != null) {
            return Response.ok(sandwich).build();
        } else {
            JsonObject jsonError = Json.createObjectBuilder().
                    add("error", "Ce sandwich n'existe pas.").build();
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

    @POST
    @Path("/{sandwichId}/addTaille")
    public Response addTaille(@PathParam("sandwichId") String sandwichId, Taille taille, @Context UriInfo uriInfo){
        Sandwich sdw = this.sdwResource.findById(sandwichId);
        if (sdw != null) {
            if (taille != null) {
                Sandwich newSandwich = this.sdwResource.ajouteTaille(sdw, taille);
                URI uri = uriInfo.getAbsolutePathBuilder().path(newSandwich.getId()).build();
                return Response.created(uri)
                        .entity(newSandwich)
                        .build();
            }else{
                JsonObject jsonError = Json.createObjectBuilder().
                        add("error", "Aucune taille n'est passée en paramètre.").build();
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }else {
            JsonObject jsonError = Json.createObjectBuilder().
                    add("error", "Le sandwich sur lequel vous voulez préciser une taille n'a pas été créé.").build();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/{sandwichId}/addPain")
    public Response addPain(@PathParam("sandwichId") String sandwichId, Pain pain, @Context UriInfo uriInfo){
        Sandwich sdw = this.sdwResource.findById(sandwichId);
        if (sdw != null){
            if(pain != null){
                Sandwich newSandwich = this.sdwResource.ajoutePain(sdw, pain);
                URI uri = uriInfo.getAbsolutePathBuilder().path(newSandwich.getId()).build();
                return Response.created(uri)
                        .entity(newSandwich)
                        .build();
            }else{
                JsonObject jsonError = Json.createObjectBuilder().
                        add("error", "Aucun pain n'est passée en paramètre.").build();
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }else{
            JsonObject jsonError = Json.createObjectBuilder().
                    add("error", "Le sandwich sur lequel vous voulez mettre un pain n'a pas été créé.").build();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/{sandwichId}/addIngredients/{ingredientId}")
    public Response addIngredients(@PathParam("sandwichId") String sandwichId, @PathParam("ingredientId") String ingredientId, @Context UriInfo uriInfo){
        Sandwich sdw = this.sdwResource.findById(sandwichId);
        if(sdw != null){
            Ingredient ing = this.ingResource.findById(ingredientId);
            if (ing != null){
                Sandwich newSandwich = this.sdwResource.ajouteIngredients(sdw, ing);
                URI uri = uriInfo.getAbsolutePathBuilder().path(newSandwich.getId()).build();
                return Response.created(uri)
                        .entity(newSandwich)
                        .build();
            }else{
                JsonObject jsonError = Json.createObjectBuilder().
                        add("error", "Ingrédient non conforme ou inexistant.").build();
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }else{
            JsonObject jsonError = Json.createObjectBuilder().
                    add("error", "Le sandwich sur lequel vous voulez ajouter un ingrédients n'a pas été créé.").build();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    @DELETE
    @Path("/{sandwichId}")
    public void deleteSandwich(@PathParam("sandwichId") String id) {
        this.sdwResource.delete(id);
    }
}
