package com.yp.BloodBankApplication.Controller;

import com.yp.BloodBankApplication.Entity.BloodRequest;
import com.yp.BloodBankApplication.Requests.BloodReqRequest;
import com.yp.BloodBankApplication.Services.BloodRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bloodRequest")
public class BloodRequestController {

    @Autowired
    private BloodRequestService bloodRequestService;

    @PostMapping("/add/{hospitalId}")
    public ResponseEntity<BloodRequest> insertBloodRequest(@RequestBody BloodReqRequest bloodReqRequest, @PathVariable int hospitalId){
        BloodRequest bloodRequest = bloodRequestService.addBloodRequest(bloodReqRequest,hospitalId);
        return new ResponseEntity<>(bloodRequest, HttpStatus.OK);
    }
}
