package mcb.com.api.security;

import mcb.com.api.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * JwtAuthenticationFilter is a custom filter responsible for authenticating and authorizing incoming requests
 * based on JWT (JSON Web Token) tokens provided in the "Authorization" header.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // The value for the "Authorization" header that indicates the presence of a JWT token
    @Value("${jwt.header.string}")
    public String HEADER_STRING;

    // The value for the token prefix, e.g., "Bearer" (e.g., "Bearer eyJhbGciOiJIUzUxMiJ9...")
    @Value("${jwt.token.prefix}")
    public String TOKEN_PREFIX;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private TokenProvider jwtTokenUtil;

    /**
     * This method is invoked for each incoming HTTP request to perform the JWT token authentication and authorization.
     *
     * @param req   The incoming HTTP request.
     * @param res   The HTTP response object.
     * @param chain The filter chain.
     * @throws IOException      If an I/O error occurs during the filtering process.
     * @throws ServletException If a generic servlet error occurs during the filtering process.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);
        String username = null;
        String authToken = null;

        // Check if the "Authorization" header exists and starts with the token prefix (e.g., "Bearer")
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            authToken = header.replace(TOKEN_PREFIX, "");
            username = jwtTokenUtil.getUsernameFromToken(authToken);
        }

        // Authentication and authorization logic based on JWT token
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                // Create an authentication token based on the validated JWT token
                UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthenticationToken(authToken, SecurityContextHolder.getContext().getAuthentication(), userDetails);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

                // Set the authenticated user's authentication in the security context
                logger.info("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // Continue the filter chain to the next filter or endpoint
        chain.doFilter(req, res);
    }
}

