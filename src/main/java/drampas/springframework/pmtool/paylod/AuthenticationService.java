package drampas.springframework.pmtool.paylod;

import drampas.springframework.pmtool.domain.PmUser;
import drampas.springframework.pmtool.security.JwtTokenProvider;
import drampas.springframework.pmtool.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public RegisterResponse register(PmUser pmUser){
        PmUser savedUser=userDetailsService.saveUser(pmUser);
        String token= jwtTokenProvider.generateToken(savedUser);
        return new RegisterResponse(savedUser,token);
    }

    public LoginResponse login(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword())
        );
        //the authentication manager checks if the user exists
        var user=userDetailsService.loadUserByUsername(request.getUsername());
        String token=jwtTokenProvider.generateToken(user);
        return new LoginResponse(true,token);
    }
}
