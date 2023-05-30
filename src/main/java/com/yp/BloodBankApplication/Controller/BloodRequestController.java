package com.yp.BloodBankApplication.Controller;

import com.yp.BloodBankApplication.Entity.BloodRequest;
import com.yp.BloodBankApplication.Requests.BloodReqRequest;
import com.yp.BloodBankApplication.Services.BloodRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bloodRequest")
public class BloodRequestController {

    @Autowired
    private BloodRequestService bloodRequestService;

    @PostMapping("/add")
    public ResponseEntity<BloodRequest> insertBloodRequest(@RequestBody BloodReqRequest bloodReqRequest){
        BloodRequest bloodRequest = bloodRequestService.addBloodRequest(bloodReqRequest);
        return new ResponseEntity<>(bloodRequest, HttpStatus.OK);
    }
}
