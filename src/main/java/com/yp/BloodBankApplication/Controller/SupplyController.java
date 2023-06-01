package com.yp.BloodBankApplication.Controller;

import com.yp.BloodBankApplication.Entity.SupplyReport;
import com.yp.BloodBankApplication.Services.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/supply")
public class SupplyController {

    @Autowired
    private SupplyService supplyService;

    @PostMapping("/add/{bloodRequestId}/{bloodBankId}")
    public ResponseEntity<SupplyReport> createSupplyReport(@PathVariable int bloodRequestId,
                                                          @PathVariable int bloodBankId){
        return new ResponseEntity<>(supplyService.addSupplyReport(bloodRequestId,
                bloodBankId), HttpStatus.OK);

    }

    @DeleteMapping("/delete/{supplyId}")
    public ResponseEntity<String> deleteSupplyReport(@PathVariable int supplyId){
        return new ResponseEntity<>(supplyService.deleteSupplyReport(supplyId),HttpStatus.OK);
    }
}
