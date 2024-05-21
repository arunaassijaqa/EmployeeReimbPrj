package com.Revature.Utils;



import com.Revature.Models.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//This Util Class will make sure the requests hitting our server have a JWT in the Authorization header

@Component //OncePerRequestFilter guarantees a single execution per request.
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtUtil;

    @Autowired
    public JwtTokenFilter(JwtTokenUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    //This method is called for each request that hits our server
    //It checks if the request has a valid JWT in the Authorization header
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        System.out.println("doFilterInternal: Start here");
        //If the Authorization header of the request doesn’t contain a Bearer token,
        //it continues the filter chain without updating authentication context.
        if (!hasAuthorizationBearer(request)) {
            filterChain.doFilter(request, response);
            System.out.println("JwtTokenFilter :hello you're missing a bearer token!");
            return;
        }

        //If the Authorization header contains a Bearer token...
        //extract the token to try verifying it.
        String token = getAccessToken(request);
        System.out.println(token);

        //if the token is not valid (expired, malformed, etc.)
        //continue the filter chain without updating authentication context.
        if (!jwtUtil.validateAccessToken(token)) {
            System.out.println("doFilterInternal: Return here");
            filterChain.doFilter(request, response);
            return;
        }

        //if the token IS verified,
        //update the authentication context with the user details (ID and username).
        setAuthenticationContext(token, request);

        System.out.println("doFilterInternal: Call filterChain here");
        filterChain.doFilter(request, response);
        System.out.println("doFilterInternal: end here");
    }

    //This method extracts the Authorization header in our HTTP request to check for the JWT
    //(The first check in our filter)
    private boolean hasAuthorizationBearer(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
            return false;
        }
        return true;
    }

    //This method extracts the JWT from the Authorization header
    //(For use in the second check in our filter)
    private String getAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        System.out.println(header);
        String token = header.split(" ")[1].trim();
        System.out.println("after split : "+token);
        return token;
    }

    /* this method extracts the user details from the JWT token,
    creating an Authentication object with these details,
    and setting this Authentication object in the security context.
    (necessary for Spring Security to work) */
    private void setAuthenticationContext(String token, HttpServletRequest request) {

        System.out.println("setAuthenticationContext:inside start");
        UserDetails userDetails = getUserDetails(token);

        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(userDetails, null, null);

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        System.out.println("setAuthenticationContext:inside end here");
    }

    //This is used to extract the userId and username from the JWT in the method above
    private UserDetails getUserDetails(String token) {
        User userDetails = new User();

        //use the extractor methods we wrote in JwtTokenUtil to get the userId and username
        userDetails.setUserId(jwtUtil.extractUserId(token));
        userDetails.setUsername(jwtUtil.extractUsername(token));

        System.out.println(userDetails);

        return userDetails;
    }

}
