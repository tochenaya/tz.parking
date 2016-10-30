package tz.parking.pojo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "parkingData")
public class ParkingData {
    private String number;
    private String manufacturer;
    private String inDate;
    private String outDate;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public String getOutDate() {
        return outDate;
    }

    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }
}
