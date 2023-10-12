package drampas.springframework.pmtool.web;

import drampas.springframework.pmtool.domain.PmUser;
import drampas.springframework.pmtool.services.PmUserService;
import drampas.springframework.pmtool.services.ValidationService;
import drampas.springframework.pmtool.validator.UserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class PmUserController {

    private final PmUserService pmUserService;
    private final ValidationService validationService;
    private final UserValidator userValidator;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody PmUser user, BindingResult result){
        userValidator.validate(user,result);
        ResponseEntity<?> errorMap=validationService.validationErrorMap(result);
        if(errorMap!=null){
            return errorMap;
        }
        PmUser savedUser=pmUserService.saveUser(user);
        return new ResponseEntity<PmUser>(savedUser, HttpStatus.CREATED);
    }
}
