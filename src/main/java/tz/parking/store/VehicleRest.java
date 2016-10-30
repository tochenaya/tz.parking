package tz.parking.store;

import tz.parking.pojo.response.ReportResponse;
import tz.parking.pojo.response.VehicleResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/vehicle")
public interface VehicleRest {

    @Path("/getParkingVehicles")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public VehicleResponse getParkingVehicles();

    @Path("/vehicleIsParking")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ReportResponse vehicleIsParking(@QueryParam("number") String number);
}
