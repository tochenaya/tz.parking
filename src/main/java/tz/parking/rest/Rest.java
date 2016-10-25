package tz.parking.rest;

import tz.parking.entity.Vehicle;
import tz.parking.service.ParkingService;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Path("/")
public class Rest {

    @EJB
    ParkingService parkingService;

    @Path("/hello")
    @GET
    public Response hello() {
        return Response.ok("hello").build();
    }

    @Path("/getParkingVehicles")
    @GET
    public Response getParkingVehicles() {
        List<Vehicle> vehicles = parkingService.getParkingVehicles();

        StringBuilder stringBuilder = new StringBuilder();
        for (Vehicle vehicle : vehicles) {
            stringBuilder.append(vehicle.toString() + "<br>");
        }
        return Response.ok("<div>" + stringBuilder + "</div>").build();
    }

    @Path("/vehicleIsParking")
    @GET
    public Response vehicleIsParking(@QueryParam("number") String number) {
        Boolean vehicleIsParking = parkingService.vehicleIsParking(number);
        return Response.ok("<div>" + vehicleIsParking + "</div>").build();
    }

    @Path("/getNumberOfFreePlaces")
    @GET
    public Response getNumberOfFreePlaces() {
        Integer i = parkingService.getNumberOfFreePlaces();
        return Response.ok("<div>" + i + "</div>").build();
    }

    @Path("/getProfitPerDay")
    @GET
    public Response getProfitPerDay(@QueryParam("day") String date) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Integer profit = 0;
        try {
            Date d = simpleDateFormat.parse(date);
            profit = parkingService.getProfitPerDay(d);
        } catch (ParseException e) {
            return Response.ok(e.getMessage()).build();
        }

        return Response.ok("<div>" + profit + "</div>").build();
    }
}

