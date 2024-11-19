package org.example.controller;


import org.example.dto.PontoColetaDto;
import org.example.exceptions.ExceptionNotCreated;
import org.example.exceptions.ExceptionNotFound;
import org.example.exceptions.ExceptionNotUpdate;
import org.example.model.PontoColeta;
import org.example.service.PontoColetaService;
import org.example.service.PontoColetaServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/pontoColeta")
public class PontoColetaController {

    private PontoColetaService pontoColetaService = PontoColetaServiceFactory.create();


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPontosColeta(){
        try{
            List<PontoColeta> pontos = pontoColetaService.getAllPontoColeta();
            List<PontoColetaDto> dtos = pontos.stream().map(this::mapToDto).collect(Collectors.toList());
            return Response.ok(dtos).build();
        }catch(SQLException e){
            return Response.serverError().entity(Map.of("mensagem", "não foi possível listar os registros")).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPontosById(@PathParam("id") Long id){
        try{
            PontoColeta pontoColeta = pontoColetaService.getPontoColetaById(id);
            if(pontoColeta != null){
                return Response.ok(mapToDto(pontoColeta)).build();
            }else{
                return Response.status(Response.Status.NOT_FOUND).entity(Map.of("mensagem", "ID não existente")).build();
            }
        }catch(SQLException e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("mensagem", "ID não existente")).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPontosColeta(PontoColetaDto pontoColetaDto){
        try{
            PontoColeta pontoColeta = new PontoColeta();
            pontoColeta.setLocalizacao(pontoColetaDto.getLocalizacao());
            pontoColetaService.createPontoColeta(pontoColeta);
            return Response.status(Response.Status.CREATED).entity(pontoColeta).build();
        } catch (SQLException | ExceptionNotCreated e) {
            return Response.serverError().entity(Map.of("mensagem", "não foi possível salvar o registro")).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletePontoColeta(@PathParam("id") Long id){
        try{
            pontoColetaService.deletePontoColeta(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        }catch(SQLException e){
            return Response.serverError().entity(e.getMessage()).build();
        } catch (ExceptionNotFound e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePontoColeta(@PathParam("id") Long id, PontoColetaDto pontoColetaDto){
        try{
            PontoColeta pontoColeta = pontoColetaService.getPontoColetaById(id);
            if (pontoColeta != null){
                pontoColeta.setLocalizacao(pontoColetaDto.getLocalizacao());
                pontoColetaService.updatePontoColeta(pontoColeta);
                return Response.ok(pontoColeta).build();
            }else{
                return Response.status(Response.Status.NOT_FOUND).entity(Map.of("mensagem", "ID não existente")).build();
            }
        } catch (SQLException | ExceptionNotUpdate e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }





    private PontoColetaDto mapToDto(PontoColeta pontoColeta){
        PontoColetaDto pontoColetaDto = new PontoColetaDto();
        pontoColetaDto.setId(pontoColeta.getId());
        pontoColetaDto.setLocalizacao(pontoColeta.getLocalizacao());
        return pontoColetaDto;
    }
}
