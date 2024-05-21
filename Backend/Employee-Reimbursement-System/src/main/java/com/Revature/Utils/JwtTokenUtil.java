package com.Revature.Utils;

import com.Revature.Models.User;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

//This Util Class is what generates our JWTs
@Component
public class JwtTokenUtil {
    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour

    @Value("${app.jwt.secret}") //taken out of application.properties
    private String SECRET_KEY;

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);


    //verify a given JWT. It returns true if the JWT is verified, or false otherwise.
    public boolean validateAccessToken(String token) {
        System.out.println("validateAccessToken: in validate");
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            System.out.println("validateAccessToken SUCCESS");
            return true;
        } catch (ExpiredJwtException ex) {
            LOGGER.error("JWT expired", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            LOGGER.error("Token is null, empty or only whitespace", ex.getMessage());
        } catch (MalformedJwtException ex) {
            LOGGER.error("JWT is invalid", ex);
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("JWT is not supported", ex);
        } catch (SignatureException ex) {
            LOGGER.error("Signature validation failed");
        }

        return false;
    }

    public String generateAccessToken(User u) {
        return Jwts.builder()
                .setSubject(String.format("%s", u.getUserId())) //subject is typically ID
                .claim("username", u.getUsername()) //any other data can be set as a claim
                .claim("firstname", u.getFirstname())
                .claim("lastname", u.getLastname())
                .claim("role", u.getRole())
                .setIssuer("Project2")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    //Gets the value of the subject field of a given token.
    //The subject contains User ID and email, which will be used to recreate a User object.
    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }


    //we need this method to get the userID from the JWT (stored in the subject)
    //the subject tends to be used for unique identifiers
    public int extractUserId(String token) {
        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
        String subject = claimsJws.getBody().getSubject();
        return Integer.parseInt(subject);
    }

    //we need this method to get the username (and any other claim) from the JWT
    //claims tend to be used for other information about the user (role would be another good claim)
    public String extractUsername(String token) {
        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
        return claimsJws.getBody().get("username", String.class);
    }

    //for extract role from token
    public String extractRole(String token) {
        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
        return claimsJws.getBody().get("role", String.class);
    }


}