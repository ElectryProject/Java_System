package org.example.controller;


import org.example.dto.MensagemDto;
import org.example.exceptions.ExceptionNotCreated;
import org.example.exceptions.ExceptionNotFound;
import org.example.exceptions.ExceptionNotUpdate;
import org.example.model.Mensagem;
import org.example.service.MensagemService;
import org.example.service.MensagemServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/mensagem")
public class MensagemController {

    private MensagemService mensagemService = MensagemServiceFactory.create();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMensagem() {
        try{
            List<Mensagem> mensagens = mensagemService.getAllMensagem();
            List<MensagemDto> dtos = mensagens.stream().map(this::mapToDto).collect(Collectors.toList());
            return Response.ok(dtos).build();
        } catch (SQLException e) {
            return Response.serverError().entity(Map.of("mensagem", "não foi possível listar os registros")).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMensagemById(@PathParam("id") Long id) {
        try{
            Mensagem mensagem = mensagemService.getMensagemById(id);
            if (mensagem != null) {
                return Response.ok(mapToDto(mensagem)).build();
            }else{
                return Response.status(Response.Status.NOT_FOUND).entity(Map.of("mensagem", "ID não existente")).build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("mensagem", "ID não existente")).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMensagem(MensagemDto mensagemDto) {
        try{
            Mensagem mensagem = new Mensagem();
            mensagem.setNome(mensagemDto.getNome());
            mensagem.setEmail(mensagemDto.getEmail());
            mensagem.setAssunto(mensagemDto.getAssunto());
            mensagem.setMensagem(mensagemDto.getMensagem());
            mensagemService.createMensagem(mensagem);
            return Response.status(Response.Status.CREATED).entity(mensagem).build();
        }catch (SQLException | ExceptionNotCreated e){
            return Response.serverError().entity(Map.of("mensagem", "não foi possível salvar o registro")).build();
        }
    }


    @DELETE
    @Path("/{id}")
    public Response deleteMensagemById(@PathParam("id") Long id) {
        try{
            mensagemService.deleteMensagem(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (SQLException e) {
            return Response.serverError().entity(e.getMessage()).build();
        } catch (ExceptionNotFound e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMensagem(@PathParam("id") Long id, MensagemDto mensagemDto){
        try{
            Mensagem mensagem = mensagemService.getMensagemById(id);
            if (mensagem != null){
                mensagem.setNome(mensagemDto.getNome());
                mensagem.setEmail(mensagemDto.getEmail());
                mensagem.setAssunto(mensagemDto.getAssunto());
                mensagem.setMensagem(mensagemDto.getMensagem());
                mensagemService.updateMensagem(mensagem);
                return Response.ok(mensagem).build();
            }else{
                return Response.status(Response.Status.NOT_FOUND).entity(Map.of("mensagem", "ID não existente")).build();
            }
        } catch (SQLException | ExceptionNotUpdate e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }





    private MensagemDto mapToDto(Mensagem mensagem) {
        MensagemDto mensagemDto = new MensagemDto();
        mensagemDto.setId(mensagem.getId());
        mensagemDto.setNome(mensagem.getNome());
        mensagemDto.setEmail(mensagem.getEmail());
        mensagemDto.setAssunto(mensagem.getAssunto());
        mensagemDto.setMensagem(mensagem.getMensagem());
        return mensagemDto;
    }
}
