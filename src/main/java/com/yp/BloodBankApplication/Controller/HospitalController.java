package com.yp.BloodBankApplication.Controller;

import com.yp.BloodBankApplication.Entity.Hospital;
import com.yp.BloodBankApplication.Services.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @PostMapping("/add")
    public ResponseEntity<Hospital> insertHospital(@RequestBody Hospital hospital){
        Hospital hospitalResponse = hospitalService.addHospital(hospital);
        return new ResponseEntity<>(hospitalResponse,HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Hospital> updateHospital(@RequestBody Hospital hospital){
        Hospital hospitalResponse = hospitalService.updateHospital(hospital);
        return new ResponseEntity<>(hospitalResponse, HttpStatus.OK);
    }
}
