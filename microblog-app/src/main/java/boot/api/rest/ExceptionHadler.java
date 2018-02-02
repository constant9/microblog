package boot.api.rest;

import boot.api.rest.dto.AlreadyExitsErrorResponse;
import boot.api.rest.dto.NotFoundErrorResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class ExceptionHadler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundErrorResponse.class)
    public final ResponseEntity<Details> handleException(NotFoundErrorResponse ex, WebRequest request) {
        Details errorDetails = new Details(ex.getTimestamp(), ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

	@ExceptionHandler(AlreadyExitsErrorResponse.class)
	public final ResponseEntity<Details> handleException(AlreadyExitsErrorResponse ex, WebRequest request) {
		Details errorDetails = new Details(ex.getTimestamp(), ex.getMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}


    public static class Details {
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
        private Date timestamp = new Date();
        private String message;

		public Details(String message) {
			this.message = message;
		}

		public Details(Date timestamp, String message) {
            this.timestamp = timestamp;
            this.message = message;
        }

		public Date getTimestamp() {
			return timestamp;
		}

		public String getMessage() {
			return message;
		}
	}
}

