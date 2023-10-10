package drampas.springframework.pmtool.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<Object> handleProjectIdentifierException(ProjectIdentifierException exception, WebRequest request){
        ProjectIdentifierExceptionResponse response=new ProjectIdentifierExceptionResponse(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<Object> handleProjectNotFoundException(ProjectNotFoundException exception,WebRequest request){
        ProjectNotFoundResponse response=new ProjectNotFoundResponse(exception.getMessage());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
}
