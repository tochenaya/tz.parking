package tz.parking.pojo.response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "report")
public class ReportResponse extends Response {
    private Boolean vehicleIsParking;
    private Integer numberOfFreePlaces;
    private Integer profitPerDay;

    public Boolean getVehicleIsParking() {
        return vehicleIsParking;
    }

    public void setVehicleIsParking(Boolean vehicleIsParking) {
        this.vehicleIsParking = vehicleIsParking;
    }

    public Integer getNumberOfFreePlaces() {
        return numberOfFreePlaces;
    }

    public void setNumberOfFreePlaces(Integer numberOfFreePlaces) {
        this.numberOfFreePlaces = numberOfFreePlaces;
    }

    public Integer getProfitPerDay() {
        return profitPerDay;
    }

    public void setProfitPerDay(Integer profitPerDay) {
        this.profitPerDay = profitPerDay;
    }
}
