package org.example.controller;


import org.example.dto.DescarteDto;
import org.example.exceptions.ExceptionNotCreated;
import org.example.exceptions.ExceptionNotFound;
import org.example.exceptions.ExceptionNotUpdate;
import org.example.model.Descarte;
import org.example.service.DescarteService;
import org.example.service.DescarteServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/descarte")
public class DescarteController {

    private DescarteService descarteService = DescarteServiceFactory.create();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDescartes() {
        try{
            List<Descarte> descartes = descarteService.getAllDescarte();
            List<DescarteDto> dtos = descartes.stream().map(this::mapToDto).collect(Collectors.toList());
            return Response.ok(dtos).build();
        }catch(SQLException e){
            return Response.serverError().entity(Map.of("mensagem", "não foi possível listar os registros")).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDescarteById(@PathParam("id") Long id) {
        try{
            Descarte descarte = descarteService.getDescarteById(id);
            if (descarte != null) {
                return Response.ok(mapToDto(descarte)).build();
            }else{
                return Response.status(Response.Status.NOT_FOUND).entity(Map.of("mensagem", "ID não existente")).build();
            }
        } catch (SQLException e) {
            return Response.serverError().entity(Map.of("mensagem", "ID não existente")).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addDescarte(DescarteDto descarteDto) {
        try{
            Descarte descarte = new Descarte();
            descarte.setProduto(descarteDto.getProduto());
            descarte.setDataDescarte(descarteDto.getDataDescarte());
            descarteService.createDescarte(descarte);
            return Response.status(Response.Status.CREATED).entity(descarte).build();
        } catch (SQLException | ExceptionNotCreated e) {
            return Response.serverError().entity(Map.of("mensagem", "não foi possível salvar o registro")).build();
        }


    }

    @DELETE
    @Path("/{id}")
    public Response deleteDescarte(@PathParam("id") Long id) {
        try{
            descarteService.deleteDescarte(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        }catch (SQLException e){
            return Response.serverError().entity(e.getMessage()).build();
        } catch (ExceptionNotFound e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDescarte(@PathParam("id") Long id, DescarteDto descarteDto) {
        try{
            Descarte descarte = descarteService.getDescarteById(id);
            if (descarte != null) {
                descarte.setProduto(descarteDto.getProduto());
                descarte.setDataDescarte(descarteDto.getDataDescarte());
                descarteService.updateDescarte(descarte);
                return Response.ok(descarte).build();
            }else{
                return Response.status(Response.Status.NOT_FOUND).entity(Map.of("mensagem", "ID não existente")).build();
            }
        } catch (SQLException | ExceptionNotUpdate e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }





    private DescarteDto mapToDto(Descarte descarte) {
        DescarteDto descarteDto = new DescarteDto();
        descarteDto.setId(descarte.getId());
        descarteDto.setProduto(descarte.getProduto());
        descarteDto.setDataDescarte(descarte.getDataDescarte());
        return descarteDto;
    }
}
