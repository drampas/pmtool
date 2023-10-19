package drampas.springframework.pmtool.security;

public class SecurityConstants {

    public static final String SIGN_UP_URLS = "/api/users/**";
    public static final String SECRET ="404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    public static final String TOKEN_PREFIX= "Bearer ";//it should always be "Bearer " with the space
    public static final String HEADER_STRING = "Authorization";//it should always be "Authorization"
    public static final long EXPIRATION_TIME = 1000*60*24;//24 hours
}
