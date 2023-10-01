package drampas.springframework.pmtool.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProjectNotFoundException extends RuntimeException{
    public ProjectNotFoundException() {
        super();
    }
    public ProjectNotFoundException(String message){
        super(message);
    }
}
