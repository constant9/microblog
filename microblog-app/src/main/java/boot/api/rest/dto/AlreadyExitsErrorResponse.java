package boot.api.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyExitsErrorResponse extends RuntimeException {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp = new Date();
    private String message;

    public AlreadyExitsErrorResponse(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String getMessage() {
        return message;
    }
}