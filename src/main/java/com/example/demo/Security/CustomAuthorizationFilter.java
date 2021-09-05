package com.example.demo.Security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private static final String APPLICATION_JSON_VALUE = null;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // if they're trying to go to the login page, we let them
        if (request.getServletPath().equals("/login")) {
            filterChain.doFilter(request, response);
        }
        // else we get their token and see what resources they can access
        else {
            // we get the authorization header
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            // if the authorization header has the word "Bearer ", it means we have a token
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    String token = authorizationHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                    JWTVerifier verifyToken = JWT.require(algorithm).build();
                    DecodedJWT decodedToken = verifyToken.verify(token);
                    String username = decodedToken.getSubject();
                    // get the roles as an array of string
                    String[] roles = decodedToken.getClaim("roles").asArray(String.class);
                    // makes a collection of SimpleGrantedAuthority
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    // convert each role of type String to type SimpleGrantedAuthority
                    for (int i = 0; i < roles.length; i++) {
                        authorities.add(new SimpleGrantedAuthority(roles[i]));
                    }
                    // get the usernamepasswordauthentication token
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            username, null, authorities);

                    // give the SecurityContextHolder the details of the currently logged in user
                    // and let it determine what resources the user can access.
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                }
                // if the token is invalid, we return an error message
                catch (Exception e) {
                    response.setHeader("error", e.getMessage());
                    Map<String, String> error = new HashMap<String, String>();
                    error.put("error_message", e.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);

                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }
            }
            // if there is no token
            else {
                filterChain.doFilter(request, response);
            }

        }
    }

}
