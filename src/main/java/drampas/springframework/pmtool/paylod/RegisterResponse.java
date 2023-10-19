package drampas.springframework.pmtool.paylod;

import drampas.springframework.pmtool.domain.PmUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {

    private PmUser pmUser;
    private String token;
}
