package tz.parking.store.impl;

import tz.parking.pojo.response.ReportResponse;
import tz.parking.pojo.response.VehicleResponse;
import tz.parking.service.VehicleService;
import tz.parking.store.VehicleRest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.QueryParam;
import java.util.List;

@Stateless
public class VehicleRestBean implements VehicleRest {

    @EJB
    VehicleService vehicleService;

    @Override
    public VehicleResponse getParkingVehicles() {
        List<tz.parking.entity.Vehicle> vehicles = vehicleService.getParkingVehicles();
        VehicleResponse vehicleResponse = new VehicleResponse();
        vehicleResponse.setVehicles(vehicles);
        return vehicleResponse;
    }

    @Override
    public ReportResponse vehicleIsParking(@QueryParam("number") String number) {
        Boolean vehicleIsParking = vehicleService.vehicleIsParking(number);
        ReportResponse reportResponse = new ReportResponse();
        reportResponse.setVehicleIsParking(vehicleIsParking);
        return reportResponse;
    }
}
