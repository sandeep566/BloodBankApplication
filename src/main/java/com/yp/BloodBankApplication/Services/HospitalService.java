package com.yp.BloodBankApplication.Services;

import com.yp.BloodBankApplication.Entity.Hospital;
import com.yp.BloodBankApplication.Exception.HospitalNotFoundException;
import com.yp.BloodBankApplication.Repository.HospitalRepository;
import com.yp.BloodBankApplication.Requests.HospitalRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



/**
 * This class provides services related to hospitals.
 *
 * Collections used
 * java.util.List: Used to store lists of hospitals.
 */
@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;


    /**
     * Adds a new hospital.
     *
     * @param hospitalRequest   The hospital information to be added.
     * @return The added hospital.
     */
    public Hospital addHospital(HospitalRequest hospitalRequest){
        Hospital hospital = new Hospital(hospitalRequest.getHospitalId(), hospitalRequest.getHospitalName(), hospitalRequest.getAddress(), hospitalRequest.getPhoneNo(), new ArrayList<>());
        return hospitalRepository.save(hospital);
    }


    /**
     * Updates an existing hospital.
     *
     * @param hospitalRequest   The updated hospital information.
     * @return The updated hospital.
     * @throws HospitalNotFoundException if the hospital with the given ID is not found.
     */
    public Hospital updateHospital(HospitalRequest hospitalRequest){
        Optional<Hospital> optionalHospital = hospitalRepository.findById(hospitalRequest.getHospitalId());
        if(optionalHospital.isPresent()){
            Hospital hospital1 = optionalHospital.get();
            hospital1.setHospitalName(hospitalRequest.getHospitalName());
            hospital1.setAddress(hospitalRequest.getAddress());
            hospital1.setPhoneNo(hospitalRequest.getPhoneNo());
            return hospitalRepository.save(hospital1);
        }
        throw new HospitalNotFoundException("Hospital Not Found");
    }


    /**
     * Retrieves a hospital by ID.
     *
     * @param hospitalId    The ID of the hospital to retrieve.
     * @return The hospital with the specified ID.
     * @throws HospitalNotFoundException if the hospital with the given ID is not found.
     */
    public Hospital getHospital(int hospitalId){
        Optional<Hospital> hospital = hospitalRepository.findById(hospitalId);
        if(hospital.isPresent()){
            return hospital.get();
        }
        throw new HospitalNotFoundException("Hospital not found");
    }

    /**
     * Deletes a hospital by ID.
     *
     * @param hospitalId    The ID of the hospital to delete.
     * @return A confirmation message for the deleted hospital.
     * @throws HospitalNotFoundException if the hospital with the given ID is not found.
     */
    public String deleteHospital(int hospitalId){
        Optional<Hospital> hospital = hospitalRepository.findById(hospitalId);
        if(hospital.isPresent()){
            hospitalRepository.deleteById(hospitalId);
            return "Hospital Deleted";
        }
        throw new HospitalNotFoundException("Hospital not found");
    }


    /**
     * Retrieves a list of all hospitals.
     *
     * @return A list of all hospitals.
     */
    public List<Hospital> viewAllHospitals(){
        return hospitalRepository.findAll();
    }


}
