package drampas.springframework.pmtool.exeptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidLoginResponse {
    private String username;
    private String password;
    //setting up the constructor that way in order to get this specific response
    public InvalidLoginResponse() {
        this.username ="Invalid username";
        this.password ="Invalid password";
    }
}
