package drampas.springframework.pmtool.exeptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
//created just to pass an object and not a string to the responseEntityHandler
public class ProjectIdentifierExceptionResponse {
    private String projectIdentifier;
}
