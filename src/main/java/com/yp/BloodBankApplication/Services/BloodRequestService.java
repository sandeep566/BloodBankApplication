package com.yp.BloodBankApplication.Services;

import com.yp.BloodBankApplication.Entity.BloodRequest;
import com.yp.BloodBankApplication.Entity.Hospital;
import com.yp.BloodBankApplication.Exception.HospitalNotFoundException;
import com.yp.BloodBankApplication.Repository.BloodRequestRepository;
import com.yp.BloodBankApplication.Repository.HospitalRepository;
import com.yp.BloodBankApplication.Requests.BloodReqRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class BloodRequestService {
    
    @Autowired
    private BloodRequestRepository bloodRequestRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    public BloodRequest addBloodRequest(BloodReqRequest bloodRequest,int hospitalId){
        Optional<Hospital> hospital = hospitalRepository.findById(hospitalId);
        if(hospital.isPresent()){
            BloodRequest bloodRequest1 = new BloodRequest(bloodRequest.getId(), bloodRequest.getName(), bloodRequest.getAge(),bloodRequest.getBloodGroup(),bloodRequest.getPriority(),hospital.get());
            return bloodRequestRepository.save(bloodRequest1);
        }
        throw new HospitalNotFoundException("Hospital Not Found");
    }
}
