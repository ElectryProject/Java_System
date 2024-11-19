package org.example.controller;


import org.example.dto.FeedbackDto;
import org.example.exceptions.ExceptionNotCreated;
import org.example.exceptions.ExceptionNotFound;
import org.example.exceptions.ExceptionNotUpdate;
import org.example.exceptions.FeedbackInvalidException;
import org.example.model.Feedback;
import org.example.service.FeedbackService;
import org.example.service.FeedbackServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/feedback")
public class FeedbackController {

    private FeedbackService feedbackService = FeedbackServiceFactory.create();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllFeedback(){
        try{
            List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
            List<FeedbackDto> dtos = feedbacks.stream().map(this::mapToDto).collect(Collectors.toList());
            return Response.ok(dtos).build();
        } catch (SQLException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFeedbackById(@PathParam("id") Long id){
        try{
            Feedback feedback = feedbackService.getFeedbackById(id);
            if (feedback != null){
                return Response.ok(mapToDto(feedback)).build();
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
    public Response addFeedback(FeedbackDto feedbackDto){
        try{
            Feedback feedback = new Feedback();
            feedback.setComentarioAvaliativo(feedbackDto.getComentarioAvaliativo());
            feedback.setPontuacaoAvaliativa(feedbackDto.getPontuacaoAvaliativa());
            feedbackService.createFeedback(feedback);
            return Response.status(Response.Status.CREATED).entity(feedback).build();
        } catch (SQLException | ExceptionNotCreated e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("mensagem", "não foi possível salvar o registro")).build();
        } catch (FeedbackInvalidException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }


    @DELETE
    @Path("/{id}")
    public Response deleteFeedback(@PathParam("id") Long id){
        try{
            feedbackService.deleteFeedback(id);
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
    public Response updateFeedback(@PathParam("id") Long id, FeedbackDto feedbackDto){
        try{
            Feedback feedback = feedbackService.getFeedbackById(id);
            if (feedback != null){
                feedback.setComentarioAvaliativo(feedbackDto.getComentarioAvaliativo());
                feedback.setPontuacaoAvaliativa(feedbackDto.getPontuacaoAvaliativa());
                feedbackService.updateFeedback(feedback);
                return Response.ok(feedback).build();
            }else{
                return Response.status(Response.Status.NOT_FOUND).entity(Map.of("mensagem", "ID não existente")).build();
            }
        } catch (SQLException | ExceptionNotUpdate e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }



    private FeedbackDto mapToDto(Feedback feedback){
        FeedbackDto feedbackDto = new FeedbackDto();
        feedbackDto.setId(feedback.getId());
        feedbackDto.setComentarioAvaliativo(feedback.getComentarioAvaliativo());
        feedbackDto.setPontuacaoAvaliativa(feedback.getPontuacaoAvaliativa());
        return feedbackDto;
    }


}
