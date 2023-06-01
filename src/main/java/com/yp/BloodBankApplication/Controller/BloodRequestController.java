package com.yp.BloodBankApplication.Controller;

import com.yp.BloodBankApplication.Entity.BloodRequest;
import com.yp.BloodBankApplication.Enums.BloodGroup;
import com.yp.BloodBankApplication.Enums.IsSupplied;
import com.yp.BloodBankApplication.Enums.Priority;
import com.yp.BloodBankApplication.Requests.BloodReqRequest;
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

    @PostMapping("/add/{hospitalId}")
    public ResponseEntity<BloodRequest> insertBloodRequest(@RequestBody BloodReqRequest bloodReqRequest,
                                                           @PathVariable int hospitalId,
                                                           @RequestParam BloodGroup bloodGroup,
                                                           @RequestParam Priority priority){
        BloodRequest bloodRequest = bloodRequestService.addBloodRequest(bloodReqRequest,hospitalId,bloodGroup,priority);
        return new ResponseEntity<>(bloodRequest, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<BloodRequest> updateBloodRequest(@RequestBody BloodReqRequest bloodReqRequest,
                                                           @RequestParam("bloodGroup")BloodGroup bloodGroup,
                                                           @RequestParam("priority")Priority priority,
                                                           @RequestParam("isSupplied")IsSupplied isSupplied){
        return new ResponseEntity<>(bloodRequestService.updateBloodRequest(bloodReqRequest,bloodGroup,priority,isSupplied),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{bloodRequestId}")
    public ResponseEntity<String> removeBloodRequest(@PathVariable int bloodRequestId){
        return new ResponseEntity<>(bloodRequestService.deleteBloodRequest(bloodRequestId),HttpStatus.OK);
    }

    @GetMapping("/viewAll")
    public ResponseEntity<List<BloodRequest>> viewAllRequests(){
        return new ResponseEntity<>(bloodRequestService.getAllBloodRequests(),HttpStatus.OK);
    }

    @GetMapping("/view/{requestId}")
    public ResponseEntity<BloodRequest> viewBloodRequest(int requestId){
        return new ResponseEntity<>(bloodRequestService.viewBloodRequest(requestId),HttpStatus.OK);
    }

    @GetMapping("/viewAllByHospital/{hospitalId}")
    public ResponseEntity<List<BloodRequest>> viewAllRequestsByHospital(int hospitalId){
        return new ResponseEntity<>(bloodRequestService.viewBloodRequestByHospitalId(hospitalId),HttpStatus.OK);
    }
}
