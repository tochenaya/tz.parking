package tz.parking.service;

import tz.parking.entity.Order;
import tz.parking.entity.Vehicle;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Stateless
public class ParkingService {

    private static final Integer PARKING_CAPACITY = 10;
    private static final Integer COST_PER_HOUR = 100;

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

    public Integer getNumberOfFreePlaces() {
        Long l = entityManager.createQuery("select count(o) from Order o where o.departureTime is null", Long.class).getSingleResult();
        return PARKING_CAPACITY - l.intValue();
    }

    //date with time 00:00:00
    public Integer getProfitPerDay(@NotNull Date date) {
        List<Order> orders = entityManager.createQuery("select o from Order o where DATE(o.arrivalTime) <= :date " +
                "AND (o.departureTime is null OR DATE(o.departureTime) >= :date)", Order.class)
                .setParameter("date", date).getResultList();

        LocalDate input = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int sum = 0;
        for (Order order : orders) {
            Date start = order.getArrivalTime();
            if (start.compareTo(date) < 0) {
                start = date; // от начала дня
            }

            Date finish = order.getDepartureTime();
            LocalDate departureDate = order.getDepartureTime() != null ? order.getDepartureTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
            if (departureDate == null && now.equals(input)) {
                finish = new Date(); //до текущего времени
            } else if (departureDate == null && !now.equals(input) ||
                    (departureDate != null && !departureDate.equals(input))) {
                finish = new Date(date.getTime() + 24 * 60 * 60 * 1000); //до конца дня
            }

            int hours = (int) Math.ceil((finish.getTime() - start.getTime()) / 1000 / 60 / 60d);
            sum += hours * COST_PER_HOUR;
        }
        return sum;
    }
}
