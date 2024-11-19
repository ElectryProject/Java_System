package org.example.controller;


import org.example.dto.TokenDto;
import org.example.exceptions.ExceptionNotCreated;
import org.example.exceptions.ExceptionNotFound;
import org.example.exceptions.ExceptionNotUpdate;
import org.example.model.Token;
import org.example.service.TokenService;
import org.example.service.TokenServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/token")
public class TokenController {

    private TokenService tokenService = TokenServiceFactory.create();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTokens() {
        try{
            List<Token> tokens = tokenService.getAllTokens();
            List<TokenDto> dtos = tokens.stream().map(this::mapToDto).collect(Collectors.toList());
            return Response.ok(dtos).build();
        }catch(SQLException e){
            return Response.serverError().entity(Map.of("mensagem", "não foi possível listar os registros")).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTokenById(@PathParam("id") Long id) {
        try{
            Token token = tokenService.getTokenById(id);
            if (token != null) {
                return Response.ok(mapToDto(token)).build();
            }else{
                return Response.status(Response.Status.NOT_FOUND).entity(Map.of("mensagem", "ID não existente")).build();
            }
        } catch (SQLException e) {
            return Response.serverError().entity(Map.of("mensagem", "ID não existente")).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addToken(TokenDto tokenDto) {
        try{
            Token token = new Token();
            token.setCodigoToken(tokenDto.getCodigoToken());
            tokenService.createToken(token);
            return Response.status(Response.Status.CREATED).entity(token).build();
        } catch (SQLException | ExceptionNotCreated e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("mensagem", "não foi possível salvar o registro")).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteToken(@PathParam("id") Long id) {
        try{
            tokenService.deleteToken(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (ExceptionNotFound e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateToken(@PathParam("id") Long id, TokenDto tokenDto){
        try{
            Token token = tokenService.getTokenById(id);
            if (token != null){
                token.setCodigoToken(tokenDto.getCodigoToken());
                tokenService.updateToken(token);
                return Response.ok(token).build();
            }else{
                return Response.status(Response.Status.NOT_FOUND).entity(Map.of("mensagem", "ID não existente")).build();
            }
        } catch (ExceptionNotUpdate | SQLException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }



    private TokenDto mapToDto(Token token){
        TokenDto tokenDto = new TokenDto();
        tokenDto.setId(token.getId());
        tokenDto.setCodigoToken(token.getCodigoToken());
        return tokenDto;
    }
}
