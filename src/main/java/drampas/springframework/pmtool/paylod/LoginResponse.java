package drampas.springframework.pmtool.paylod;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginResponse {

    private boolean success;
    private String token;

    public boolean isSuccess(){
        return success;
    }
    @Override
    public String toString(){
        return "JWTLoginSuccessResponse{" +
                "success=" + success +
                ", token='" + token + '\'' +
                '}';
    }
}
