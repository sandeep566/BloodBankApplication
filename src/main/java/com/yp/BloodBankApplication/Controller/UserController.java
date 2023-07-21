/**
 * The {@code UserController} class handles the RESTful API endpoints related to user operations in the Blood Bank Application.
 */

package com.yp.BloodBankApplication.Controller;

import com.yp.BloodBankApplication.Configuration.JwtService;
import com.yp.BloodBankApplication.Entity.BloodBank;
import com.yp.BloodBankApplication.Entity.Hospital;
import com.yp.BloodBankApplication.Entity.User;
import com.yp.BloodBankApplication.Repository.BloodBankRepository;
import com.yp.BloodBankApplication.Repository.HospitalRepository;
import com.yp.BloodBankApplication.Repository.UserRepository;
import com.yp.BloodBankApplication.Requests.AuthRequest;
import com.yp.BloodBankApplication.Response.JwtResponse;
import com.yp.BloodBankApplication.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin("http://localhost:4200")
public class UserController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;




    /**
     * Authenticates a user and returns a JWT token.
     *
     * @param authRequest the authentication request containing the username and password
     * @return the JWT token
     * @throws UsernameNotFoundException if the user is not found or the authentication fails
     */
    @PostMapping("/authenticate")
    public JwtResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest) throws UsernameNotFoundException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        Optional<User> user = userRepository.findByUserName(authRequest.getUsername());
        if(authentication.isAuthenticated()){
            String token = jwtService.generateToken(authRequest.getUsername(),user.get().getRole());
            return new JwtResponse(user.get(),token);
        }else{
            throw new UsernameNotFoundException("Invalid User Request!");
        }
    }


    /**
     * Resets the password for a user.
     *
     * @param user the user with the new password
     * @return a message indicating the password reset status
     */
    @PutMapping("/reset")
    public String resetPassword(@RequestBody User user){
        return userService.resetPassword(user);
    }
}
