package com.yp.BloodBankApplication.Services;

import com.yp.BloodBankApplication.Entity.BloodBank;
import com.yp.BloodBankApplication.Entity.BloodRequest;
import com.yp.BloodBankApplication.Entity.Hospital;
import com.yp.BloodBankApplication.Exception.BloodBankNotFoundException;
import com.yp.BloodBankApplication.Exception.BloodRequestNotFoundException;
import com.yp.BloodBankApplication.Exception.HospitalNotFoundException;
import com.yp.BloodBankApplication.Repository.BloodRequestRepository;
import com.yp.BloodBankApplication.Repository.HospitalRepository;
import com.yp.BloodBankApplication.Requests.BloodReqRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
            BloodRequest bloodRequest1 = new BloodRequest(bloodRequest.getId(),
                    bloodRequest.getName(), bloodRequest.getAge(),
                    bloodRequest.getBloodGroup(),
                    bloodRequest.getPriority(),hospital.get(), bloodRequest.getQuantity());
            return bloodRequestRepository.save(bloodRequest1);
        }
        throw new HospitalNotFoundException("Hospital Not Found");
    }

    public BloodRequest updateBloodRequest(BloodReqRequest bloodReqRequest){
        BloodRequest bloodRequest = bloodRequestRepository.findById(bloodReqRequest.getId()).orElse(null);
        if(bloodRequest != null){
            bloodRequest.setPatientName(bloodReqRequest.getName());
            bloodRequest.setAge(bloodReqRequest.getAge());
            bloodRequest.setBloodGroup(bloodReqRequest.getBloodGroup());
            bloodRequest.setPriority(bloodReqRequest.getPriority());
            bloodRequest.setQuantity(bloodReqRequest.getQuantity());
            return bloodRequestRepository.save(bloodRequest);
        }
        throw new BloodRequestNotFoundException("Blood Request Not Found");
    }


    public String deleteBloodRequest(int id){
        BloodRequest bloodRequest = bloodRequestRepository.findById(id).orElse(null);
        if(bloodRequest != null){
            bloodRequestRepository.deleteById(id);
            return "Blood Request Deleted";
        }
        throw new BloodRequestNotFoundException("Blood Request Not Found Exception");
    }


    public List<BloodRequest> getAllBloodRequests(){
        return bloodRequestRepository.findAll();
    }

    public BloodRequest viewBloodRequest(int requestId){
        BloodRequest bloodRequest = bloodRequestRepository.findById(requestId).orElse(null);
        if(bloodRequest != null){
            return bloodRequest;
        }
        throw new BloodBankNotFoundException("Blood Request Not Found");
    }

    public List<BloodRequest> viewBloodRequestByHospitalId(int hospitalId){
        Hospital hospital = hospitalRepository.findById(hospitalId).orElse(null);
        if(hospital != null){
            return hospital.getBloodRequests();
        }
        throw new HospitalNotFoundException("Hospital not Found");
    }
}
