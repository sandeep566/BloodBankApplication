/**
 * The BloodRequestController class handles HTTP requests related to blood requests in the Blood Bank Application.
 * It provides endpoints for adding, updating, deleting, and retrieving blood requests.
 */
package com.yp.BloodBankApplication.Controller;

import com.yp.BloodBankApplication.Entity.BloodRequest;
import com.yp.BloodBankApplication.Enums.BloodGroup;
import com.yp.BloodBankApplication.Enums.Priority;
import com.yp.BloodBankApplication.Requests.BloodBankHospitalRequest;
import com.yp.BloodBankApplication.Services.BloodRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bloodRequest")
public class BloodRequestController {

    @Autowired
    private BloodRequestService bloodRequestService;

    /**
     * Endpoint to add a new blood request for a hospital.
     *
     * @param bloodBankHospitalRequest The blood request details.
     * @param hospitalId              The ID of the hospital.
     * @param bloodGroup              The blood group for the request.
     * @param priority                The priority level of the request.
     * @return ResponseEntity containing the newly created blood request and HTTP status code.
     */

    @PostMapping("/add/{hospitalId}")
    public ResponseEntity<BloodRequest> insertBloodRequest(@RequestBody BloodBankHospitalRequest bloodBankHospitalRequest,
                                                           @PathVariable int hospitalId,
                                                           @RequestParam BloodGroup bloodGroup,
                                                           @RequestParam Priority priority){
        BloodRequest bloodRequest = bloodRequestService.addBloodRequest(bloodBankHospitalRequest,hospitalId,bloodGroup,priority);
        return new ResponseEntity<>(bloodRequest, HttpStatus.OK);
    }

    /**
     * Endpoint to update an existing blood request.
     *
     * @param bloodBankHospitalRequest The updated blood request details.
     * @param bloodGroup              The updated blood group for the request.
     * @param priority                The updated priority level of the request.
     * @param isSupplied              The updated supply status of the request.
     * @return ResponseEntity containing the updated blood request and HTTP status code.
     */

    @PutMapping("/update")
    public ResponseEntity<BloodRequest> updateBloodRequest(@RequestBody BloodBankHospitalRequest bloodBankHospitalRequest,
                                                           @RequestParam("bloodGroup")BloodGroup bloodGroup,
                                                           @RequestParam("priority")Priority priority,
                                                           @RequestParam("isSupplied")boolean isSupplied){
        return new ResponseEntity<>(bloodRequestService.updateBloodRequest(bloodBankHospitalRequest,bloodGroup,priority,isSupplied),HttpStatus.OK);
    }


    /**
     * Endpoint to delete a blood request.
     *
     * @param bloodRequestId The ID of the blood request to be deleted.
     * @return ResponseEntity containing a success message and HTTP status code.
     */
    @DeleteMapping("/delete/{bloodRequestId}")
    public ResponseEntity<String> removeBloodRequest(@PathVariable int bloodRequestId){
        return new ResponseEntity<>(bloodRequestService.deleteBloodRequest(bloodRequestId),HttpStatus.OK);
    }


    /**
     * Endpoint to retrieve all blood requests.
     *
     * @return ResponseEntity containing a list of all blood requests and HTTP status code.
     */
    @GetMapping("/viewAll")
    public ResponseEntity<List<BloodRequest>> viewAllRequests(){
        return new ResponseEntity<>(bloodRequestService.getAllBloodRequests(),HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a specific blood request by ID.
     *
     * @param requestId The ID of the blood request.
     * @return ResponseEntity containing the blood request and HTTP status code.
     */
    @GetMapping("/view/{requestId}")
    public ResponseEntity<BloodRequest> viewBloodRequest(@PathVariable int requestId){
        return new ResponseEntity<>(bloodRequestService.viewBloodRequest(requestId),HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve all blood requests for a specific hospital.
     *
     * @param hospitalId The ID of the hospital.
     * @return ResponseEntity containing a list of blood requests for the hospital and HTTP status code.
     */
    @GetMapping("/viewAllByHospital/{hospitalId}")
    public ResponseEntity<List<BloodRequest>> viewAllRequestsByHospital(@PathVariable int hospitalId){
        return new ResponseEntity<>(bloodRequestService.viewBloodRequestByHospitalId(hospitalId),HttpStatus.OK);
    }
}
