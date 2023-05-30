package com.yp.BloodBankApplication.Services;

import com.yp.BloodBankApplication.Entity.Hospital;
import com.yp.BloodBankApplication.Exception.HospitalNotFoundException;
import com.yp.BloodBankApplication.Repository.HospitalRepository;
import com.yp.BloodBankApplication.Requests.HospitalRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;


@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    public Hospital addHospital(HospitalRequest hospitalRequest){
        Hospital hospital = new Hospital(hospitalRequest.getHospitalId(), hospitalRequest.getHospitalName(), hospitalRequest.getAddress(), hospitalRequest.getPhoneNo(), new ArrayList<>());
        return hospitalRepository.save(hospital);
    }

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


}
