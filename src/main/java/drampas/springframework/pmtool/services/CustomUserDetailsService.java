package drampas.springframework.pmtool.services;

import drampas.springframework.pmtool.domain.PmUser;
import drampas.springframework.pmtool.exeptions.UsernameAlreadyExistsException;
import drampas.springframework.pmtool.repositories.PmUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final PmUserRepository pmUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PmUser user=pmUserRepository.findByUsername(username);
        if (user==null){
            new UsernameNotFoundException("User not found");
        }
        return user;
    }
    public PmUser saveUser(PmUser newUser){
        try {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            newUser.setConfirmPassword("");//do not show the password in the response
            return pmUserRepository.save(newUser);
        }catch (Exception e){
            throw new UsernameAlreadyExistsException("Username:"+newUser.getUsername()+" already exists");
        }
    }
}
