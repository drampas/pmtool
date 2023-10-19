package drampas.springframework.pmtool.security;

import drampas.springframework.pmtool.domain.PmUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static drampas.springframework.pmtool.security.SecurityConstants.EXPIRATION_TIME;
import static drampas.springframework.pmtool.security.SecurityConstants.SECRET;

@Service
public class JwtTokenProvider {

    public String generateToken(Map<String,Object> claims, UserDetails userDetails){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256,getSignInKey())
                .compact();
    }
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

    public String extractUserName(String jwt){
        return extractClaim(jwt,Claims::getSubject);//the subject of the token should be the username
    }

    public boolean isTokenValid(String jwt,UserDetails userDetails){
        final String username=extractUserName(jwt);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwt));
    }

    private boolean isTokenExpired(String jwt) {
        Date expiryDate=extractClaim(jwt,Claims::getExpiration);
        return expiryDate.before(new Date());
    }

    public <T> T extractClaim(String jwt, Function<Claims,T> claimsResolver){
        final Claims claims=extractAllClaims(jwt);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwt){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
