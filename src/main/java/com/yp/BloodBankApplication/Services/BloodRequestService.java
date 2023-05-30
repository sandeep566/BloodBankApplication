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

    public BloodRequest addBloodRequest(BloodReqRequest bloodRequest){
        Optional<Hospital> hospital = hospitalRepository.findByHospitalName(bloodRequest.getHospitalName());
        if(hospital.isPresent()){
            BloodRequest bloodRequest1 = new BloodRequest();
            bloodRequest1.setPatientName(bloodRequest.getName());
            bloodRequest1.setAge(bloodRequest.getAge());
            bloodRequest1.setHospital(hospital.get());
            bloodRequestRepository.save(bloodRequest1);
        }
        throw new HospitalNotFoundException("Hospital Not Found");
    }
}
