package mcb.com.api.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * Component responsible for handling unauthorized requests and returning an HTTP 401 Unauthorized response.
 */
@Component
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint, Serializable {

    /**
     * Invoked when an unauthenticated user attempts to access a secured resource.
     *
     * @param request       The incoming HTTP request.
     * @param response      The HTTP response.
     * @param authException The authentication exception that occurred.
     * @throws IOException If an I/O error occurs while processing the response.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // Respond with an HTTP 401 Unauthorized status code and message "Unauthorized"
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");

        // Alternatively, you can throw a custom SecurityViolationException if needed:
        // throw new SecurityViolationException(MessageUtil.UNAUTHORIZE);
    }
}
