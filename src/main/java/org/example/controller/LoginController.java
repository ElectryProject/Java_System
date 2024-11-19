package org.example.controller;


import org.example.dto.LoginDto;
import org.example.exceptions.ExceptionNotCreated;
import org.example.exceptions.ExceptionNotFound;
import org.example.exceptions.ExceptionNotUpdate;
import org.example.exceptions.SenhaInvalidException;
import org.example.model.Login;
import org.example.model.Usuario;
import org.example.service.LoginService;
import org.example.service.LoginServiceFactory;
import org.example.service.UsuarioService;
import org.example.service.UsuarioServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/login")
public class LoginController {

    private LoginService loginService = LoginServiceFactory.create();


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLogins(){
        try {
            List<Login> logins = loginService.getAllLogins();
            List<LoginDto> dtos = logins.stream().map(this::mapToDto).collect(Collectors.toList());
            return Response.ok(dtos).build();
        }catch (SQLException e){
            return Response.serverError().entity(Map.of("mensagem", "não foi possível listar os registros")).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLoginById(@PathParam("id") Long id){
        try{
            Login login = loginService.getLoginById(id);
            if (login != null){
                return Response.ok(mapToDto(login)).build();
            }else{
                return Response.status(Response.Status.NOT_FOUND).entity(Map.of("mensagem", "ID não existente")).build();
            }
        }catch (SQLException e){
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addLogin(LoginDto loginDto){
        try{
            Login login = new Login();
            login.setUsername(loginDto.getUsername());
            login.setSenha(loginDto.getSenha());
            loginService.createLogin(login);
            return Response.status(Response.Status.CREATED).entity(login).build();
        } catch (SQLException | ExceptionNotCreated e) {
            return Response.serverError().entity(Map.of("mensagem", "não foi possível salvar o registro")).build();
        } catch (SenhaInvalidException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteLogin(@PathParam("id") Long id){
        try{
            loginService.deleteLogin(id);
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
    public Response updateLogin(@PathParam("id") Long id, LoginDto loginDto){
        try{
            Login login = loginService.getLoginById(id);
            if (login != null){
                login.setUsername(loginDto.getUsername());
                login.setSenha(loginDto.getSenha());
                loginService.updateLogin(login);
                return Response.ok(login).build();
            }else{
                return Response.status(Response.Status.NOT_FOUND).entity(Map.of("mensagem", "ID não existente")).build();
            }
        } catch (SQLException | ExceptionNotUpdate e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }





    private LoginDto mapToDto(Login login){
        LoginDto loginDto = new LoginDto();
        loginDto.setId(login.getId());
        loginDto.setUsername(login.getUsername());
        loginDto.setSenha(login.getSenha());
        return loginDto;
    }
}
