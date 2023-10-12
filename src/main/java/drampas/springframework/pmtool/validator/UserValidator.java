package drampas.springframework.pmtool.validator;

import drampas.springframework.pmtool.domain.PmUser;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return PmUser.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PmUser user=(PmUser) target;
        if(!user.getPassword().equals(user.getConfirmPassword())){
            errors.rejectValue("confirmPassword","Match", "Passwords must match");
        }
    }
}
