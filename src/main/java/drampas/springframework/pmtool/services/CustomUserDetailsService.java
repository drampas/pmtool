package drampas.springframework.pmtool.services;

import drampas.springframework.pmtool.domain.PmUser;
import drampas.springframework.pmtool.repositories.PmUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final PmUserRepository pmUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PmUser user=pmUserRepository.findByUsername(username);
        if (user==null){
            new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
