package boot.api.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.Date;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundErrorResponse extends RuntimeException {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp = new Date();
    private String message;

    public NotFoundErrorResponse(String message) {
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