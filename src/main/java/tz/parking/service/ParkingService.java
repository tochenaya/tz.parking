package tz.parking.service;

import org.hibernate.Session;
import tz.parking.db.HibernateUtil;
import tz.parking.entity.Order;
import tz.parking.entity.Vehicle;

import javax.ejb.Stateless;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Stateless
public class ParkingService {

    public static final Integer PARKING_CAPACITY = 10;
    public static final Integer COST_PER_HOUR = 100;

    public List<Vehicle> getParkingVehicles() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Vehicle> vehicles = session.createQuery("select o.vehicle from Order o where o.departureTime is null").list();
        session.close();
        return vehicles;

    }

    public Boolean vehicleIsParking(String number) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Long l = (Long) session.createQuery("select count(o.vehicle) from Order o where o.departureTime is null and o.vehicle.number = :number").
                setParameter("number", number).uniqueResult();
        session.close();
        return l != 0 ? true : false;
    }

    public Integer getNumberOfFreePlaces() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Long l = (Long) session.createQuery("select count(o) from Order o where o.departureTime is null").uniqueResult();
        session.close();
        return PARKING_CAPACITY - l.intValue();
    }

    //date with time 00:00:00
    public Integer getProfitPerDay(Date date) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Order> orders = session.createQuery("select o from Order o where DATE(o.arrivalTime) <= :date AND (o.departureTime is null OR DATE(o.departureTime) >= :date)")
                .setParameter("date", date).list();

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
            if (order.getDepartureTime() == null && now.equals(input)) {
                finish = new Date(); //до текущего времени
            } else if (order.getDepartureTime() == null && !now.equals(input) ||
                    (order.getDepartureTime() != null && !departureDate.equals(input))) {
                finish = new Date(date.getTime() + 24 * 60 * 60 * 1000); //до конца дня
            }

            int hours = (int) Math.ceil((finish.getTime() - start.getTime()) / 1000 / 60 / 60d);
            sum += hours * COST_PER_HOUR;
        }
        session.close();
        return sum;
    }
}
