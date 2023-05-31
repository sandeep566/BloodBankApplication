package com.yp.BloodBankApplication.Controller;

import com.yp.BloodBankApplication.Entity.Hospital;
import com.yp.BloodBankApplication.Requests.HospitalRequest;
import com.yp.BloodBankApplication.Services.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @PostMapping("/add")
    public ResponseEntity<Hospital> insertHospital(@RequestBody HospitalRequest hospitalRequest){
        Hospital hospitalResponse = hospitalService.addHospital(hospitalRequest);
        return new ResponseEntity<>(hospitalResponse,HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Hospital> updateHospital(@RequestBody HospitalRequest hospitalRequest){
        Hospital hospitalResponse = hospitalService.updateHospital(hospitalRequest);
        return new ResponseEntity<>(hospitalResponse, HttpStatus.OK);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<Hospital> getHospital(int id){
        return new ResponseEntity<>(hospitalService.viewHospital(id),HttpStatus.FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> removeHospital(int id){
        return new ResponseEntity<>(hospitalService.deleteHospital(id),HttpStatus.OK);
    }

    @GetMapping("/viewAll")
    public ResponseEntity<List<Hospital>> getAllHospitals(){
        return new ResponseEntity<>(hospitalService.viewAllHospitals(),HttpStatus.OK);
    }
}
