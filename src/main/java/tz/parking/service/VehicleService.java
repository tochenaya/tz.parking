package tz.parking.service;

import tz.parking.entity.Vehicle;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class VehicleService {

    @PersistenceContext(unitName = "parkingPU")
    private EntityManager entityManager;

    public List<Vehicle> getParkingVehicles() {
        return entityManager.createQuery("select o.vehicle from Order o where o.departureTime is null", Vehicle.class)
                .getResultList();
    }

    public Boolean vehicleIsParking(String number) {
        Long l = entityManager.createQuery("select count(o.vehicle) from Order o " +
                "where o.departureTime is null and o.vehicle.number = :number", Long.class)
                .setParameter("number", number).getSingleResult();
        return l != 0;
    }



}
