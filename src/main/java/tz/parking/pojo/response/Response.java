package tz.parking.pojo.response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class Response{

    private Boolean status;
    private Integer errorCode;
    private String errorMessage;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
