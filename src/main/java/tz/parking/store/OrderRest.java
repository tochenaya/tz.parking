package tz.parking.store;

import tz.parking.pojo.ParkingData;
import tz.parking.pojo.response.ReportResponse;
import tz.parking.pojo.response.Response;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/order")
public interface OrderRest {

    @Path("/getNumberOfFreePlaces")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ReportResponse getNumberOfFreePlaces();

    @Path("/getProfitPerDay")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ReportResponse getProfitPerDay(@QueryParam("day") String date);

    @Path("parkingVehicle")
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public Response parkingVehicle(ParkingData parkingData);

    @Path("unParkingVehicle")
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public Response unParkingVehicle(ParkingData parkingData);


}
