package com.yp.BloodBankApplication.Controller;

import com.yp.BloodBankApplication.Configuration.JwtService;
import com.yp.BloodBankApplication.Entity.BloodBank;
import com.yp.BloodBankApplication.Entity.Donor;
import com.yp.BloodBankApplication.Enums.BloodGroup;
import com.yp.BloodBankApplication.Requests.AuthRequest;
import com.yp.BloodBankApplication.Requests.BloodBankRequest;
import com.yp.BloodBankApplication.Services.BloodBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * Controller class for handling blood bank-related API endpoints.
 */
@RestController
@RequestMapping("/bloodBank")
public class BloodBankController {

    @Autowired
    public BloodBankService bloodBankService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;


    /**
     * Adds a new blood bank.
     *
     * @param bloodBankRequest The details of the blood bank to be added.
     * @return The added blood bank.
     */

    @PostMapping("/add")
    public ResponseEntity<BloodBank> addBloodBank(@RequestBody BloodBankRequest bloodBankRequest){
        return new ResponseEntity<>(bloodBankService.registerBloodBank(bloodBankRequest), HttpStatus.CREATED);
    }

    /**
     * Updates an existing blood bank.
     *
     * @param bloodBank The updated details of the blood bank.
     * @return The updated blood bank.
     */


    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BloodBank> updateBloodBank(@RequestBody BloodBankRequest bloodBank){
        return new ResponseEntity<>(bloodBankService.updateBloodBank(bloodBank),HttpStatus.OK);
    }

    /**
     * Retrieves a blood bank by its ID.
     *
     * @param id The ID of the blood bank to retrieve.
     * @return The blood bank with the specified ID.
     */

    @GetMapping("/view/{id}")
    public ResponseEntity<BloodBank> getBloodBank(@PathVariable int id){
        return new ResponseEntity<>(bloodBankService.viewBloodBank(id),HttpStatus.FOUND);
    }


    /**
     * Retrieves all blood banks.
     *
     * @return A list of all blood banks.
     */
    @GetMapping("/viewAll")
    public ResponseEntity<List<BloodBank>> getAllBloodBanks(){
        return new ResponseEntity<>(bloodBankService.viewAllBloodBanks(),HttpStatus.OK);
    }

    /**
     * Deletes a blood bank by its ID.
     *
     * @param id The ID of the blood bank to delete.
     * @return A message indicating the deletion status.
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> removeBloodBank(@PathVariable int id){
        return new ResponseEntity<>(bloodBankService.deleteBloodBank(id),HttpStatus.OK);
    }

    /**
     * Retrieves all donors associated with a specific blood bank.
     *
     * @param bloodBankId The ID of the blood bank.
     * @return A list of all donors associated with the blood bank.
     */
    @GetMapping("/viewDonors/{bloodBankId}")
    public ResponseEntity<List<Donor>> viewAllDonors(@PathVariable int bloodBankId){
        return new ResponseEntity<>(bloodBankService.getAllDonors(bloodBankId),HttpStatus.OK);
    }


    /**
     * Retrieves Map of blood group and their quantity associated with a specific blood bank.
     *
     * @param id The ID of the blood bank.
     * @return A list of all donors associated with the blood bank
     */
    @GetMapping("/viewBloodGroupQuantity/{id}")
    public ResponseEntity<Map<BloodGroup,Integer>> viewBloodGroupAndQuantity(@PathVariable int id){
        return new ResponseEntity<>(bloodBankService.viewBloodGroupAndQuantity(id),HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) throws UsernameNotFoundException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(authRequest.getUsername());
        }else{
            throw new UsernameNotFoundException("Invalid User Request!");
        }
    }


}
