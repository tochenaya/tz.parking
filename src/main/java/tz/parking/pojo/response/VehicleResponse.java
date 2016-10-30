package tz.parking.pojo.response;

import tz.parking.entity.Vehicle;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "vehicles")
public class VehicleResponse extends Response {

    private List<Vehicle> vehicles = new ArrayList<>();

    @XmlElement(name = "vehicle")
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
