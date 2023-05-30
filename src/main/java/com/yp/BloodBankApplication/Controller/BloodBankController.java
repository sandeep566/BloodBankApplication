package com.yp.BloodBankApplication.Controller;

import com.yp.BloodBankApplication.Entity.BloodBank;
import com.yp.BloodBankApplication.Requests.BloodBankRequest;
import com.yp.BloodBankApplication.Services.BloodBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bloodBank")
public class BloodBankController {

    @Autowired
    public BloodBankService bloodBankService;

    @PostMapping("/add")
    public ResponseEntity<BloodBank> addBloodBank(@RequestBody BloodBankRequest bloodBankRequest){
        return new ResponseEntity<>(bloodBankService.registerBloodBank(bloodBankRequest), HttpStatus.OK);
    }

}
