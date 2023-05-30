package com.yp.BloodBankApplication.Controller;

import com.yp.BloodBankApplication.Entity.Donor;
import com.yp.BloodBankApplication.Requests.DonorRequest;
import com.yp.BloodBankApplication.Services.DonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/donor")
public class DonorController {

    @Autowired
    private DonorService donorService;

    @PostMapping("/add/{bloodBankId}")
    public ResponseEntity<Donor> addDonor(@RequestBody DonorRequest donorRequest , @PathVariable int bloodBankId){
        return new ResponseEntity<>(donorService.registerDonor(donorRequest,bloodBankId), HttpStatus.OK);
    }
}
