/**
 * The DonorController class handles HTTP requests related to blood donors in the Blood Bank Application.
 * It provides endpoints for adding, updating, deleting, and retrieving donor information.
 */

package com.yp.BloodBankApplication.Controller;

import com.yp.BloodBankApplication.Entity.Donor;
import com.yp.BloodBankApplication.Enums.BloodGroup;
import com.yp.BloodBankApplication.Requests.DonorRequest;
import com.yp.BloodBankApplication.Services.DonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/donor")
public class DonorController {

    @Autowired
    private DonorService donorService;


    /**
     * Endpoint to register a new blood donor for a blood bank.
     *
     * @param donorRequest The details of the donor to be registered.
     * @param bloodBankId  The ID of the blood bank.
     * @param bloodGroup   The blood group of the donor.
     * @return ResponseEntity containing the newly registered donor and HTTP status code.
     */
    @PostMapping("/add/{bloodBankId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Donor> addDonor(@RequestBody DonorRequest donorRequest , @PathVariable int bloodBankId, @RequestParam("bloodGroup") BloodGroup bloodGroup){
        return new ResponseEntity<>(donorService.registerDonor(donorRequest,bloodBankId,bloodGroup), HttpStatus.OK);
    }

    /**
     * Endpoint to update an existing blood donor.
     *
     * @param donorRequest The updated details of the donor.
     * @param bloodGroup   The updated blood group of the donor.
     * @return ResponseEntity containing the updated donor and HTTP status code.
     */

    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Donor> updateDonor(@RequestBody DonorRequest donorRequest,@RequestParam("bloodGroup") BloodGroup bloodGroup){
        return new ResponseEntity<>(donorService.updateDonor(donorRequest,bloodGroup),HttpStatus.OK);
    }


    /**
     * Endpoint to retrieve a specific blood donor by ID.
     *
     * @param donorId The ID of the donor.
     * @return ResponseEntity containing the donor and HTTP status code.
     */
    @GetMapping("/view/{donorId}")
    public ResponseEntity<Donor> viewDonorById(int donorId){
        return new ResponseEntity<>(donorService.viewDonorById(donorId),HttpStatus.FOUND);
    }


    /**
     * Endpoint to retrieve all blood donors.
     *
     * @return ResponseEntity containing a list of all blood donors and HTTP status code.
     */
    @GetMapping("/viewAll")
    public ResponseEntity<List<Donor>> viewAllDonors(){
        return new ResponseEntity<>(donorService.getAllDonors(),HttpStatus.OK);
    }


    /**
     * Endpoint to retrieve all blood donors with a specific blood group.
     *
     * @param bloodGroup The blood group of the donors to retrieve.
     * @return ResponseEntity containing a list of blood donors with the specified blood group and HTTP status code.
     */
    @GetMapping("/viewAllByBloodGroup")
    public ResponseEntity<List<Donor>> viewAllDonorsByBloodGroup(@RequestParam("bloodGroup")BloodGroup bloodGroup){
        return new ResponseEntity<>(donorService.getAllDonorsByBloodGroup(bloodGroup),HttpStatus.OK);
    }


    /**
     * Endpoint to delete a blood donor.
     *
     * @param donorId The ID of the donor to be deleted.
     * @return ResponseEntity containing a success message and HTTP status code.
     */
    @DeleteMapping("/delete/{donorId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> removeDonor(@PathVariable int donorId){
        return new ResponseEntity<>(donorService.deleteDonor(donorId),HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve all blood donors within a specific age range.
     *
     * @param age The age to filter the donors by.
     * @return ResponseEntity containing a list of blood donors within the specified age range and HTTP status code.
     */
    @GetMapping("/viewDonorByAge/{age}")
    public ResponseEntity<List<Donor>> getDonorsByAge(@PathVariable int age){
        return new ResponseEntity<>(donorService.viewDonorsByAge(age),HttpStatus.FOUND);
    }

    /**
     * Endpoint to retrieve donors who have suitable blood groups.
     *
     * @param bloodGroups The list of blood groups to filter the donors by.
     * @return ResponseEntity containing a list of donors with suitable blood groups and HTTP status code.
     */
    @GetMapping("/getDonorsForSuitableBloodGroups")
    public ResponseEntity<List<Donor>> getDonorsBySuitableBloodGroup(@RequestParam List<BloodGroup> bloodGroups){
        return new ResponseEntity<>(donorService.viewDonorsBySuitableBloodGroup(bloodGroups),HttpStatus.OK);
    }
}
