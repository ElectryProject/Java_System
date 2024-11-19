package org.example.controller;


import org.example.dto.ResiduoDto;
import org.example.exceptions.ExceptionNotCreated;
import org.example.exceptions.ExceptionNotFound;
import org.example.exceptions.ExceptionNotUpdate;
import org.example.model.Residuo;
import org.example.service.ResiduoService;
import org.example.service.ResiduoServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/residuo")
public class ResiduoController {

    private ResiduoService residuoService = ResiduoServiceFactory.create();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllResiduos(){
        try{
            List<Residuo> residuos = residuoService.getAllResiduos();
            List<ResiduoDto> dtos = residuos.stream().map(this::mapToDo).collect(Collectors.toList());
            return Response.ok(dtos).build();
        }catch(SQLException e){
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getResiduoById(@PathParam("id") Long id){
        try{
            Residuo residuo = residuoService.getResiduoById(id);
            if (residuo != null){
                return Response.ok(mapToDo(residuo)).build();
            }else{
                return Response.status(Response.Status.NOT_FOUND).entity(Map.of("mensagem", "ID não existente")).build();
            }
        }catch(SQLException e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addResiduo(ResiduoDto residuoDto){
        try{
            Residuo residuo = new Residuo();
            residuo.setNomeProduto(residuoDto.getNomeProduto());
            residuo.setCategoria(residuoDto.getCategoria());
            residuo.setPontuacao(residuoDto.getPontuacao());
            residuoService.createResiduo(residuo);
            return Response.status(Response.Status.CREATED).entity(residuo).build();
        } catch (SQLException | ExceptionNotCreated e) {
            return Response.serverError().entity(Map.of("mensagem", "não foi possível salvar o registro")).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteResiduo(@PathParam("id") Long id){
        try{
            residuoService.deleteResiduo(id);
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
    public Response updateResiduo(@PathParam("id") Long id, ResiduoDto residuoDto){
        try{
            Residuo residuo = residuoService.getResiduoById(id);
            if (residuo != null){
                residuo.setNomeProduto(residuoDto.getNomeProduto());
                residuo.setCategoria(residuoDto.getCategoria());
                residuo.setPontuacao(residuoDto.getPontuacao());
                residuoService.updateResiduo(residuo);
                return Response.ok(residuo).build();
            }else{
                return Response.status(Response.Status.NOT_FOUND).entity(Map.of("mensagem", "ID não existente")).build();
            }
        } catch (SQLException | ExceptionNotUpdate e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }


    private ResiduoDto mapToDo(Residuo residuo){
        ResiduoDto residuoDto = new ResiduoDto();
        residuoDto.setId(residuo.getId());
        residuoDto.setNomeProduto(residuo.getNomeProduto());
        residuoDto.setCategoria(residuo.getCategoria());
        residuoDto.setPontuacao(residuo.getPontuacao());
        return residuoDto;
    }

}
