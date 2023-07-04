package com.yp.BloodBankApplication.Services;

import com.yp.BloodBankApplication.Entity.Hospital;
import com.yp.BloodBankApplication.Entity.User;
import com.yp.BloodBankApplication.Exception.HospitalAlreadyExistsException;
import com.yp.BloodBankApplication.Exception.HospitalNotFoundException;
import com.yp.BloodBankApplication.Repository.HospitalRepository;
import com.yp.BloodBankApplication.Repository.UserRepository;
import com.yp.BloodBankApplication.Requests.HospitalRequest;
import com.yp.BloodBankApplication.Requests.HospitalUpdateRequest;
import com.yp.BloodBankApplication.Utility.BloodBankUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.yp.BloodBankApplication.Utility.BloodBankUtil.mapToHospital;
import static com.yp.BloodBankApplication.Utility.BloodBankUtil.mapToHospitalUpdate;


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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * Adds a new hospital.
     *
     * @param hospitalRequest   The hospital information to be added.
     * @return The added hospital.
     */
    public Hospital addHospital(HospitalRequest hospitalRequest){

        if(! isUsernamePresent(hospitalRequest.getEmail())){
            if( !isPhoneNumberPresent(hospitalRequest.getPhoneNo())){
                Hospital hospital = new Hospital(hospitalRequest.getHospitalId(), hospitalRequest.getHospitalName(), hospitalRequest.getAddress(),hospitalRequest.getEmail() ,hospitalRequest.getPhoneNo(), new ArrayList<>());
                userRepository.save(BloodBankUtil.mapHospitalToUser(hospitalRequest,passwordEncoder));
                return hospitalRepository.save(hospital);
            }
            throw new HospitalAlreadyExistsException("Account already exists with this phone number");
        }
        throw new HospitalAlreadyExistsException("Account already present with this email");
    }



    /**
     * Updates an existing hospital.
     *
     * @param hospitalRequest   The updated hospital information.
     * @return The updated hospital.
     * @throws HospitalNotFoundException if the hospital with the given ID is not found.
     */
    public Hospital updateHospital(HospitalUpdateRequest hospitalRequest){
        Hospital hospital = hospitalRepository.findById(hospitalRequest.getHospitalId()).orElse(null);
        if(hospital != null){
            return hospitalRepository.save(mapToHospitalUpdate(hospital,hospitalRequest));
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
            userRepository.deleteById(hospitalId);
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


    private boolean isUsernamePresent(String username){
        return userRepository.findByUserName(username).isPresent();
    }

    private boolean isPhoneNumberPresent(long phoneNumber){
        return hospitalRepository.findByPhoneNo(phoneNumber).isPresent();
    }


}
