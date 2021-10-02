package swc3.demowebshop.exceptionHandling;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ControllerExceptionHandler {

  private ErrorMessage createErrorMessage(HttpStatus httpStatus, Exception ex, WebRequest request){
    return new ErrorMessage(
            httpStatus.value(),
            new Date(),
            ex.getMessage(),
            request.getDescription(false));
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
      var httpStatus = HttpStatus.NOT_FOUND;
      return new ResponseEntity<>(createErrorMessage(httpStatus, ex, request), httpStatus);
  }

//    @ExceptionHandler(DataAccessException.class)
//    public ResponseEntity<ErrorMessage> resourceNotFoundException(DataAccessException ex, WebRequest request) {
//        var httpStatus = HttpStatus.NOT_FOUND;
//        return new ResponseEntity<>(createErrorMessage(httpStatus, ex, request), httpStatus);
//    }

  //any other exceptions will be handled by this handler and status 500 will be returned
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
      var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
      return new ResponseEntity<>(createErrorMessage(httpStatus, ex, request), httpStatus);
  }
}
