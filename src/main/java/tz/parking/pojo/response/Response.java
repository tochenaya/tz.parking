package tz.parking.pojo.response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class Response{

    private Boolean status;
    private Integer error_code;
    private String error_message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getError_code() {
        return error_code;
    }

    public void setError_code(Integer error_code) {
        this.error_code = error_code;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

}
