package org.example.controller;


import org.example.dto.PremioEnergiaDto;
import org.example.exceptions.ExceptionNotCreated;
import org.example.exceptions.ExceptionNotFound;
import org.example.exceptions.ExceptionNotUpdate;
import org.example.model.PremioEnergia;
import org.example.service.PremioEnergiaService;
import org.example.service.PremioEnergiaServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/premioEnergia")
public class PremioEnergiaController {

    private PremioEnergiaService premioEnergiaService = PremioEnergiaServiceFactory.create();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPremios() {
        try {
            List<PremioEnergia> premios = premioEnergiaService.getAllPremios();
            List<PremioEnergiaDto> dtos = premios.stream().map(this::mapToDto).collect(Collectors.toList());
            return Response.ok(dtos).build();
        }catch (SQLException e){
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPremioById(@PathParam("id") Long id){
        try{
            PremioEnergia premioEnergia = premioEnergiaService.getPremioById(id);
            if (premioEnergia != null){
                return Response.ok(mapToDto(premioEnergia)).build();
            }else{
                return Response.status(Response.Status.NOT_FOUND).entity(Map.of("mensagem", "ID não existente")).build();
            }
        } catch (SQLException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPremio(PremioEnergiaDto premioEnergiaDto){
        try{
            PremioEnergia premioEnergia = new PremioEnergia();
            premioEnergia.setNomePremio(premioEnergiaDto.getNomePremio());
            premioEnergia.setValorPremio(premioEnergiaDto.getValorPremio());
            premioEnergiaService.createPremio(premioEnergia);
            return Response.status(Response.Status.CREATED).entity(premioEnergia).build();
        } catch (SQLException | ExceptionNotCreated e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("mensagem", "não foi possível salvar o registro")).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletePremio(@PathParam("id") Long id){
        try{
            premioEnergiaService.deletePremio(id);
            return Response.noContent().build();
        } catch (SQLException e) {
            return Response.serverError().entity(e.getMessage()).build();
        } catch (ExceptionNotFound e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePremio(@PathParam("id") Long id, PremioEnergiaDto premioEnergiaDto){
        try{
            PremioEnergia premioEnergia = premioEnergiaService.getPremioById(id);
            if (premioEnergia != null){
                premioEnergia.setNomePremio(premioEnergiaDto.getNomePremio());
                premioEnergia.setValorPremio(premioEnergiaDto.getValorPremio());
                premioEnergiaService.updatePremio(premioEnergia);
                return Response.ok(premioEnergia).build();
            }else{
                return Response.status(Response.Status.NOT_FOUND).entity(Map.of("mensagem", "ID não existente")).build();
            }
        } catch (SQLException | ExceptionNotUpdate e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }


    private PremioEnergiaDto mapToDto (PremioEnergia premioEnergia){
        PremioEnergiaDto premioEnergiaDto = new PremioEnergiaDto();
        premioEnergiaDto.setId(premioEnergia.getId());
        premioEnergiaDto.setNomePremio(premioEnergia.getNomePremio());
        premioEnergiaDto.setValorPremio(premioEnergia.getValorPremio());
        return premioEnergiaDto;
    }
}
