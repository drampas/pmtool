package drampas.springframework.pmtool.security;

import drampas.springframework.pmtool.services.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static drampas.springframework.pmtool.security.SecurityConstants.HEADER_STRING;
import static drampas.springframework.pmtool.security.SecurityConstants.TOKEN_PREFIX;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader(HEADER_STRING);
        final String jwt;
        final String username;
        if(authHeader == null || !authHeader.startsWith(TOKEN_PREFIX)){
            filterChain.doFilter(request,response);
            return;
        }
        jwt = authHeader.substring(7);//we need the string after the token prefix "Bearer "
        username = tokenProvider.extractUserName(jwt);
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails user= userDetailsService.loadUserByUsername(username);
            if(tokenProvider.isTokenValid(jwt,user)){
                UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(
                        user,null,user.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);//always pass to the next filter
    }
}
