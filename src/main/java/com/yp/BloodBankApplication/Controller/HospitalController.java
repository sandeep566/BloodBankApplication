/**
 * The HospitalController class handles HTTP requests related to hospitals in the Blood Bank Application.
 * It provides endpoints for adding, updating, deleting, and retrieving hospital information.
 */
package com.yp.BloodBankApplication.Controller;
import com.yp.BloodBankApplication.Entity.Donor;
import com.yp.BloodBankApplication.Entity.Hospital;
import com.yp.BloodBankApplication.Repository.HospitalRepository;
import com.yp.BloodBankApplication.Requests.HospitalRequest;
import com.yp.BloodBankApplication.Requests.HospitalUpdateRequest;
import com.yp.BloodBankApplication.Services.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/hospital")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private HospitalRepository hospitalRepository;

    /**
     * Endpoint to add a new hospital.
     *
     * @param hospitalRequest The details of the hospital to be added.
     * @return ResponseEntity containing the newly added hospital and HTTP status code.
     */
    @PostMapping("/add")
    public ResponseEntity<Hospital> insertHospital(@RequestBody HospitalRequest hospitalRequest){
        Hospital hospitalResponse = hospitalService.addHospital(hospitalRequest);
        return new ResponseEntity<>(hospitalResponse,HttpStatus.OK);
    }


    /**
     * Endpoint to update an existing hospital.
     *
     * @param hospitalRequest The details of the hospital to be updated.
     * @return ResponseEntity containing the updated hospital and HTTP status code.
     */
    @PutMapping("/update")
    public ResponseEntity<Hospital> updateHospital(@RequestBody HospitalUpdateRequest hospitalRequest){
        Hospital hospitalResponse = hospitalService.updateHospital(hospitalRequest);
        return new ResponseEntity<>(hospitalResponse, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a hospital by its ID.
     *
     * @param id The ID of the hospital to be retrieved.
     * @return ResponseEntity containing the retrieved hospital and HTTP status code.
     */
    @GetMapping("/view/{id}")
    public ResponseEntity<Hospital> getHospital(@PathVariable int id){
        return new ResponseEntity<>(hospitalService.getHospital(id),HttpStatus.OK);
    }

    /**
     * Endpoint to delete a hospital by its ID.
     *
     * @param id The ID of the hospital to be deleted.
     * @return ResponseEntity containing a success message and HTTP status code.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> removeHospital(@PathVariable int id){
        return new ResponseEntity<>(hospitalService.deleteHospital(id),HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve all hospitals.
     *
     * @return ResponseEntity containing a list of all hospitals and HTTP status code.
     */
    @GetMapping("/viewAll")
    public ResponseEntity<List<Hospital>> getAllHospitals(){
        return new ResponseEntity<>(hospitalService.viewAllHospitals(),HttpStatus.OK);
    }

    @GetMapping("/paginationAndSortingHospitals")
    public ResponseEntity<List<Hospital>> getUsers(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "hospitalId") String sortBy) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Hospital> pageResult = hospitalRepository.findAll(pageable);

        List<Hospital> hospitals = pageResult.getContent();

        return ResponseEntity.ok(hospitals);
    }
}
