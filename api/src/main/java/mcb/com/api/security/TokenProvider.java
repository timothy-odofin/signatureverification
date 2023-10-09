package mcb.com.api.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;
/**
 * Component responsible for JWT token generation, parsing, and validation.
 */
@Component
public class TokenProvider implements Serializable {


    @Value("${jwt.token.validity}")
    public long TOKEN_VALIDITY;

    @Value("${jwt.signing.key}")
    public String SIGNING_KEY;

    @Value("${jwt.authorities.key}")
    public String AUTHORITIES_KEY;

    @Value("${jwt.refreshExpiration}")
    public long REFRESH_TOKEN_VALIDITY;

    /**
     * Retrieves the username from the JWT token.
     *
     * @param token The JWT token.
     * @return The username extracted from the token.
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Retrieves the PID (Public Identifier) from the JWT token.
     *
     * @param token The JWT token.
     * @return The PID extracted from the token.
     */
    public String getPidFromToken(String token) {
        return getClaimFromToken(token, Claims::getId);
    }

    /**
     * Retrieves the expiration date from the JWT token.
     *
     * @param token The JWT token.
     * @return The expiration date extracted from the token.
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Generic method to retrieve a specific claim from the JWT token.
     *
     * @param token          The JWT token.
     * @param claimsResolver The claims resolver function to extract a specific claim.
     * @param <T>            The type of the claim.
     * @return The claim value extracted from the token using the provided resolver function.
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Parses and retrieves all claims from the JWT token.
     *
     * @param token The JWT token.
     * @return The parsed claims from the token.
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Checks if the JWT token is expired.
     *
     * @param token The JWT token.
     * @return True if the token is expired, false otherwise.
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * Generates a JWT token for the given user authentication and PID (Public Identifier).
     *
     * @param authentication The user authentication object.
     * @param pid            The PID (Public Identifier) of the user.
     * @return The generated JWT token.
     */
    public String generateToken(Authentication authentication, String pid) {
        return Jwts.builder()
                .setSubject(authentication.getName())
                .setId(pid)
                .claim(AUTHORITIES_KEY, getAuthorityData(authentication))
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(TOKEN_VALIDITY, ChronoUnit.MINUTES)))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }

    /**
     * Validates the JWT token against the provided user details.
     *
     * @param token       The JWT token.
     * @param userDetails The user details.
     * @return True if the token is valid for the user, false otherwise.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Retrieves the authority data (comma-separated roles) from the user authentication.
     *
     * @param authentication The user authentication object.
     * @return The authority data (comma-separated roles).
     */
    private String getAuthorityData(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    /**
     * Extracts user authentication from the JWT token.
     *
     * @param token         The JWT token.
     * @param existingAuth  The existing user authentication.
     * @param userDetails   The user details.
     * @return The extracted user authentication.
     */
    UsernamePasswordAuthenticationToken getAuthenticationToken(final String token, final Authentication existingAuth, final UserDetails userDetails) {
        final JwtParser jwtParser = Jwts.parser().setSigningKey(SIGNING_KEY);
        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
        final Claims claims = claimsJws.getBody();
        final Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }
}
