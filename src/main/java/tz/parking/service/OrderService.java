package tz.parking.service;

import tz.parking.entity.Order;
import tz.parking.entity.Vehicle;
import tz.parking.pojo.ParkingData;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Stateless
public class OrderService {

    private static final Integer PARKING_CAPACITY = 10;
    private static final Integer COST_PER_HOUR = 100;

    @PersistenceContext(unitName = "parkingPU")
    private EntityManager entityManager;

    @EJB
    private VehicleService vehicleService;

    public Integer getNumberOfFreePlaces() {
        Long l = entityManager.createQuery("select count(o) from Order o where o.departureTime is null", Long.class).getSingleResult();
        return PARKING_CAPACITY - l.intValue();
    }

    //date with time 00:00:00 in UTC
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

    public Boolean parkingVehicle(ParkingData parkingData) throws ParseException {
        if (vehicleService.vehicleIsParking(parkingData.getNumber())) return false;

        Vehicle vehicle = new Vehicle();
        vehicle.setNumber(parkingData.getNumber());
        vehicle.setManufacturer(parkingData.getManufacturer());

        Order order = new Order();
        order.setVehicle(vehicle);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date inDate = simpleDateFormat.parse(parkingData.getInDate());
        order.setArrivalTime(inDate);
        entityManager.merge(order);
        return true;
    }

    public Boolean unParkingVehicle(ParkingData parkingData) throws Exception {
        Order order = null;
        try {
            order = entityManager.createQuery("SELECT o from Order o where o.departureTime is null " +
                    "and o.vehicle.number = :number", Order.class)
                    .setParameter("number", parkingData.getNumber()).getSingleResult();

        } catch (NoResultException ex) {
            return false;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date outDate = simpleDateFormat.parse(parkingData.getOutDate());
        if(order.getArrivalTime().compareTo(outDate) > 0)
            throw new Exception("Departure time doesn't should be smaller than arrival time ");
        order.setDepartureTime(outDate);
        entityManager.persist(order);
        return true;
    }
}
