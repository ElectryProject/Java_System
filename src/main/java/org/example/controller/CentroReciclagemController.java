package org.example.controller;


import org.example.dto.CentroReciclagemDto;
import org.example.exceptions.ExceptionNotCreated;
import org.example.exceptions.ExceptionNotFound;
import org.example.exceptions.ExceptionNotUpdate;
import org.example.model.CentroReciclagem;
import org.example.service.CentroReciclagemService;
import org.example.service.CentroReciclagemServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/centroReciclagem")
public class CentroReciclagemController {

    private CentroReciclagemService centroReciclagemService = CentroReciclagemServiceFactory.create();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCentroReciclagem() {
        try{
            List<CentroReciclagem> centros = centroReciclagemService.getAllCentroReciclagem();
            List<CentroReciclagemDto> dtos = centros.stream().map(this::mapToDto).collect(Collectors.toList());
            return Response.ok(dtos).build();
        }catch(SQLException e){
            return Response.serverError().entity(Map.of("mensagem", "não foi possível listar os registros")).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCentroReciclagemById(@PathParam("id") Long id) {
        try{
            CentroReciclagem centroReciclagem = centroReciclagemService.getCentroReciclagemById(id);
            if (centroReciclagem != null) {
                return Response.ok(mapToDto(centroReciclagem)).build();
            }else{
                return Response.status(Response.Status.NOT_FOUND).entity(Map.of("mensagem", "ID não existente")).build();
            }
        }catch(SQLException e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("mensagem", "ID não existente")).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCentroReciclagem(CentroReciclagemDto centroReciclagemDto) {
        try{
            CentroReciclagem centroReciclagem = new CentroReciclagem();
            centroReciclagem.setNomeCentro(centroReciclagemDto.getNomeCentro());
            centroReciclagem.setValorResgatado(centroReciclagemDto.getValorResgatado());
            centroReciclagemService.createCentroReciclagem(centroReciclagem);
            return Response.status(Response.Status.CREATED).entity(centroReciclagem).build();
        } catch (SQLException | ExceptionNotCreated e) {
            return Response.serverError().entity(Map.of("mensagem", "não foi possível salvar o registro")).build();
        }
    }


    @DELETE
    @Path("/{id}")
    public Response deleteCentroReciclagemById(@PathParam("id") Long id) {
        try{
            centroReciclagemService.deleteCentroReciclagem(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        }catch(SQLException e){
            return Response.serverError().entity(e.getMessage()).build();
        } catch (ExceptionNotFound e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCentroReciclagem(@PathParam("id") Long id, CentroReciclagemDto centroReciclagemDto){
        try{
            CentroReciclagem centroReciclagem = centroReciclagemService.getCentroReciclagemById(id);
            if (centroReciclagem != null) {
                centroReciclagem.setNomeCentro(centroReciclagemDto.getNomeCentro());
                centroReciclagem.setValorResgatado(centroReciclagemDto.getValorResgatado());

                centroReciclagemService.updateCentroReciclagem(centroReciclagem);
                return Response.ok(centroReciclagem).build();
            }else{
                return Response.status(Response.Status.NOT_FOUND).entity(Map.of("mensagem", "ID não existente")).build();
            }
        } catch (SQLException | ExceptionNotUpdate e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }


    private CentroReciclagemDto mapToDto(CentroReciclagem centroReciclagem) {
        CentroReciclagemDto centroReciclagemDto = new CentroReciclagemDto();
        centroReciclagemDto.setId(centroReciclagem.getId());
        centroReciclagemDto.setNomeCentro(centroReciclagem.getNomeCentro());
        centroReciclagemDto.setValorResgatado(centroReciclagem.getValorResgatado());
        return centroReciclagemDto;
    }
}
