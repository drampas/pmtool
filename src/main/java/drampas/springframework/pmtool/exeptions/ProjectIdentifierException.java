package drampas.springframework.pmtool.exeptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
public class ProjectIdentifierException extends RuntimeException{

    public ProjectIdentifierException(String message){
        super(message);
    }
}
