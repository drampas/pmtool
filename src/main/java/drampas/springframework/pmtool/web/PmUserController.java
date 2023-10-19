package drampas.springframework.pmtool.web;

import drampas.springframework.pmtool.domain.PmUser;
import drampas.springframework.pmtool.paylod.AuthenticationService;
import drampas.springframework.pmtool.paylod.LoginResponse;
import drampas.springframework.pmtool.paylod.LoginRequest;
import drampas.springframework.pmtool.paylod.RegisterResponse;
import drampas.springframework.pmtool.security.JwtTokenProvider;
import drampas.springframework.pmtool.services.CustomUserDetailsService;
import drampas.springframework.pmtool.services.ValidationService;
import drampas.springframework.pmtool.validator.UserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static drampas.springframework.pmtool.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class PmUserController {

    private final CustomUserDetailsService userDetailsService;
    private final ValidationService validationService;
    private final UserValidator userValidator;
    private final AuthenticationService authenticationService;
    private final JwtTokenProvider tokenProvider;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody PmUser user, BindingResult result){
        userValidator.validate(user,result);
        ResponseEntity<?> errorMap=validationService.validationErrorMap(result);
        if(errorMap!=null){
            return errorMap;
        }
        RegisterResponse response=authenticationService.register(user);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,BindingResult result){
        ResponseEntity<?> errorMap=validationService.validationErrorMap(result);
        if(errorMap!=null){
            return errorMap;
        }
       LoginResponse response=authenticationService.login(loginRequest);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
