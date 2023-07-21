package com.yp.BloodBankApplication.Configuration;


import com.yp.BloodBankApplication.Repository.BloodBankRepository;
import com.yp.BloodBankApplication.Repository.HospitalRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


/**
 * The {@code JwtService} class provides methods for JWT (JSON Web Token) operations in the Blood Bank Application.
 * It allows for token generation, extraction, and validation.
 */
@Component
public class JwtService {


    @Autowired
    private BloodBankRepository bloodBankRepository;

    @Autowired
    private HospitalRepository hospitalRepository;
    /**
     * The secret key used for signing the JWT.
     */

    @Value("${jwt.secret}")
    public String SECRET ;


    /**
     * Extracts the username from the JWT token.
     *
     * @param token the JWT token
     * @return the username extracted from the token
     */
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }


    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token the JWT token
     * @return the expiration date extracted from the token
     */
    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }


    /**
     * Extracts a claim from the JWT token using the specified resolver function.
     *
     * @param token           the JWT token
     * @param claimsResolver  the function to resolve the desired claim from the token's claims
     * @param <T>             the type of the claim
     * @return the resolved claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Checks if the JWT token has expired.
     *
     * @param token the JWT token
     * @return {@code true} if the token has expired, {@code false} otherwise
     */
    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }


    /**
     * Validates the JWT token against the provided user details.
     *
     * @param token        the JWT token
     * @param userDetails the user details to validate against
     * @return {@code true} if the token is valid, {@code false} otherwise
     */
    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    /**
     * Generates a JWT token for the specified username.
     *
     * @param username the username
     * @return the generated JWT token
     */
    public String generateToken(String username,String role){
        Map<String, Object> claims = new HashMap<>();
        claims.put("role",role);
        if(role.equals("ROLE_ADMIN") && bloodBankRepository.findByMailAddress(username).isPresent()){
            claims.put("id",bloodBankRepository.findByMailAddress(username).get().getBloodBankId());
        } else if (role.equals("ROLE_USER") && hospitalRepository.findByEmail(username).isPresent()) {
            claims.put("id",hospitalRepository.findByEmail(username).get().getHospitalId());
        }
        return createToken(claims, username);
    }
    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();

    }

    /**
     * Retrieves the signing key for JWT verification.
     *
     * @return the signing key
     */
    public Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
