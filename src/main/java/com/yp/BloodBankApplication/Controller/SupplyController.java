/**
 * The SupplyController class handles HTTP requests related to supply reports in the Blood Bank Application.
 * It provides endpoints for creating and deleting supply reports.
 */

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


    /**
     * Endpoint to create a supply report for a blood request and blood bank.
     *
     * @param bloodRequestId The ID of the blood request.
     * @param bloodBankId    The ID of the blood bank.
     * @return ResponseEntity containing the created supply report and HTTP status code.
     */
    @PostMapping("/add/{bloodRequestId}/{bloodBankId}")
    public ResponseEntity<SupplyReport> createSupplyReport(@PathVariable int bloodRequestId,
                                                          @PathVariable int bloodBankId){
        return new ResponseEntity<>(supplyService.addSupplyReport(bloodRequestId,
                bloodBankId), HttpStatus.OK);

    }


    /**
     * Endpoint to delete a supply report by its ID.
     *
     * @param supplyId The ID of the supply report to be deleted.
     * @return ResponseEntity containing a success message and HTTP status code.
     */
    @DeleteMapping("/delete/{supplyId}")
    public ResponseEntity<String> deleteSupplyReport(@PathVariable int supplyId){
        return new ResponseEntity<>(supplyService.deleteSupplyReport(supplyId),HttpStatus.OK);
    }
}
