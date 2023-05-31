package com.yp.BloodBankApplication.Controller;

import com.yp.BloodBankApplication.Entity.Donor;
import com.yp.BloodBankApplication.Enums.BloodGroup;
import com.yp.BloodBankApplication.Requests.DonorRequest;
import com.yp.BloodBankApplication.Services.DonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/donor")
public class DonorController {

    @Autowired
    private DonorService donorService;

    @PostMapping("/add/{bloodBankId}")
    public ResponseEntity<Donor> addDonor(@RequestBody DonorRequest donorRequest , @PathVariable int bloodBankId){
        return new ResponseEntity<>(donorService.registerDonor(donorRequest,bloodBankId), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Donor> updateDonor(@RequestBody DonorRequest donorRequest){
        return new ResponseEntity<>(donorService.updateDonor(donorRequest),HttpStatus.OK);
    }

    @GetMapping("/view/{donorId}")
    public ResponseEntity<Donor> viewDonorById(int donorId){
        return new ResponseEntity<>(donorService.viewDonorById(donorId),HttpStatus.FOUND);
    }

    @GetMapping("/viewAll")
    public ResponseEntity<List<Donor>> viewAllDonors(){
        return new ResponseEntity<>(donorService.getAllDonors(),HttpStatus.OK);
    }

    @GetMapping("/viewAllByBloodGroup")
    public ResponseEntity<List<Donor>> viewAllDonorsByBloodGroup(@RequestParam("bloodGroup")BloodGroup bloodGroup){
        return new ResponseEntity<>(donorService.getAllDonorsByBloodGroup(bloodGroup),HttpStatus.OK);
    }
}
