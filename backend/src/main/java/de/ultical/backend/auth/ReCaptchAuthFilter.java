package de.ultical.backend.auth;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

@Priority(Priorities.AUTHENTICATION)
public class ReCaptchAuthFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        /*
         * Basic idea: read two header values for challenge and response, if
         * both are non-null send them to google for verification, if valid
         * authenticate user.
         */
    }

}
