package com.yp.BloodBankApplication.Services;

import com.yp.BloodBankApplication.Entity.BloodRequest;
import com.yp.BloodBankApplication.Entity.Hospital;
import com.yp.BloodBankApplication.Enums.BloodGroup;
import com.yp.BloodBankApplication.Enums.Priority;
import com.yp.BloodBankApplication.Exception.BloodBankNotFoundException;
import com.yp.BloodBankApplication.Exception.BloodRequestNotFoundException;
import com.yp.BloodBankApplication.Exception.HospitalNotFoundException;
import com.yp.BloodBankApplication.Repository.BloodRequestRepository;
import com.yp.BloodBankApplication.Repository.HospitalRepository;
import com.yp.BloodBankApplication.Requests.BloodBankHospitalRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.yp.BloodBankApplication.Utility.BloodBankUtil.mapToBloodRequest;


/**
 * This class provides services related to blood requests.
 *
 * Collections used
 * java.util.List: Used to store lists of blood requests.
 *
 */
@Service
public class BloodRequestService {
    
    @Autowired
    private BloodRequestRepository bloodRequestRepository;

    @Autowired
    private HospitalRepository hospitalRepository;




    /**
     * Adds a new blood request for a hospital.
     *
     * @param bloodRequest  The blood request information.
     * @param hospitalId    The ID of the hospital where the request is made.
     * @return The added blood request.
     * @throws HospitalNotFoundException if the hospital with the given ID is not found.
     */
    public BloodRequest addBloodRequest(BloodBankHospitalRequest bloodRequest, int hospitalId, BloodGroup bloodGroup,
                                        Priority priority){
        Optional<Hospital> hospital = hospitalRepository.findById(hospitalId);
        if(hospital.isPresent()){
            BloodRequest bloodRequest1 = new BloodRequest(bloodRequest.getId(),
                    bloodRequest.getName(), bloodRequest.getAge(),
                    bloodGroup,
                    priority,hospital.get(), bloodRequest.getQuantity(), false);
            return bloodRequestRepository.save(bloodRequest1);
        }
        throw new HospitalNotFoundException("Hospital Not Found");
    }



    /**
     * Updates an existing blood request.
     *
     * @param bloodBankHospitalRequest   The updated blood request information.
     * @return The updated blood request.
     * @throws BloodRequestNotFoundException if the blood request with the given ID is not found.
     */
    public BloodRequest updateBloodRequest(BloodBankHospitalRequest bloodBankHospitalRequest, BloodGroup bloodGroup,
                                           Priority priority, boolean isSupplied){
        BloodRequest bloodRequest = bloodRequestRepository.findById(bloodBankHospitalRequest.getId()).orElse(null);
        if(bloodRequest != null){
            mapToBloodRequest(bloodRequest,bloodBankHospitalRequest);
            bloodRequest.setBloodGroup(bloodGroup);
            bloodRequest.setPriority(priority);
            bloodRequest.setSupplied(false);
            return bloodRequestRepository.save(bloodRequest);
        }
        throw new BloodRequestNotFoundException("Blood Request Not Found");
    }


    /**
     * Deletes a blood request by ID.
     *
     * @param id    The ID of the blood request to delete.
     * @return A confirmation message for the deleted blood request.
     * @throws BloodRequestNotFoundException if the blood request with the given ID is not found.
     */

    public String deleteBloodRequest(int id){
        BloodRequest bloodRequest = bloodRequestRepository.findById(id).orElse(null);
        if(bloodRequest != null){
            bloodRequestRepository.deleteById(id);
            return "Blood Request Deleted";
        }
        throw new BloodRequestNotFoundException("Blood Request Not Found Exception");
    }



    /**
     * Retrieves a list of all blood requests.
     *
     * @return A list of all blood requests.
     */

    public List<BloodRequest> getAllBloodRequests(){
        return bloodRequestRepository.findAll();
    }


    /**
     * Retrieves a blood request by ID.
     *
     * @param requestId The ID of the blood request to retrieve.
     * @return The blood request with the specified ID.
     * @throws BloodBankNotFoundException if the blood request with the given ID is not found.
     */
    public BloodRequest viewBloodRequest(int requestId){
        BloodRequest bloodRequest = bloodRequestRepository.findById(requestId).orElse(null);
        if(bloodRequest != null){
            return bloodRequest;
        }
        throw new BloodBankNotFoundException("Blood Request Not Found");
    }



    /**
     * Retrieves a list of blood requests for a specific hospital.
     *
     * @param hospitalId    The ID of the hospital to retrieve blood requests for.
     * @return A list of blood requests for the specified hospital.
     * @throws HospitalNotFoundException if the hospital with the given ID is not found.
     */

    public List<BloodRequest> viewBloodRequestByHospitalId(int hospitalId){
        Hospital hospital = hospitalRepository.findById(hospitalId).orElse(null);
        if(hospital != null){
            return hospital.getBloodRequests();
        }
        throw new HospitalNotFoundException("Hospital not Found");
    }
}
