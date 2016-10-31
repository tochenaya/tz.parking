package tz.parking.store.impl;

import tz.parking.pojo.ParkingData;
import tz.parking.pojo.response.ReportResponse;
import tz.parking.pojo.response.Response;
import tz.parking.service.OrderService;
import tz.parking.store.OrderRest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.QueryParam;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Stateless
public class OrderRestBean implements OrderRest {

    @EJB
    OrderService orderService;

    @Override
    public ReportResponse getNumberOfFreePlaces() {
        ReportResponse reportResponse = new ReportResponse();
        reportResponse.setNumberOfFreePlaces(orderService.getNumberOfFreePlaces());
        return reportResponse;
    }

    @Override
    public ReportResponse getProfitPerDay(@QueryParam("day") String date) {
        ReportResponse reportResponse = new ReportResponse();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date d = simpleDateFormat.parse(date);
            reportResponse.setProfitPerDay(orderService.getProfitPerDay(d));
        } catch (ParseException e) {
            reportResponse.setErrorMessage(e.getMessage());
            return reportResponse;
        }

        return reportResponse;
    }

    @Override
    public Response parkingVehicle(ParkingData parkingData) {
        Response response = new Response();
        try {
            if (!orderService.parkingVehicle(parkingData)) {
                response.setStatus(false);
                response.setErrorMessage("Error: Vehicle was parked already");
                return response;
            }
        } catch (ParseException e) {
            response.setStatus(false);
            response.setErrorMessage(e.getMessage());
            return response;
        }
        response.setStatus(true);
        return response;
    }

    @Override
    public Response unParkingVehicle(ParkingData parkingData) {
        Response response = new Response();
        try {
            if (!orderService.unParkingVehicle(parkingData)) {
                response.setStatus(false);
                response.setErrorMessage("Error: Vehicle wasn't parked");
                return response;
            }
        } catch (Exception e) {
            response.setStatus(false);
            response.setErrorMessage(e.getMessage());
            return response;
        }
        response.setStatus(true);
        return response;
    }
}

