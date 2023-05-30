package com.yp.BloodBankApplication.Services;

import com.yp.BloodBankApplication.Entity.Hospital;
import com.yp.BloodBankApplication.Exception.HospitalNotFoundException;
import com.yp.BloodBankApplication.Repository.HospitalRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    public Hospital addHospital(Hospital hospital){
        return hospitalRepository.save(hospital);
    }

    public Hospital updateHospital(@NotNull Hospital hospital){
        Optional<Hospital> optionalHospital = hospitalRepository.findById(hospital.getHospitalId());
        if(optionalHospital.isPresent()){
            Hospital hospital1 = optionalHospital.get();
            hospital1.setHospitalName(hospital.getHospitalName());
            hospital1.setAddress(hospital.getAddress());
            hospital1.setPhoneNo(hospital.getPhoneNo());
            return hospitalRepository.save(hospital1);
        }
        throw new HospitalNotFoundException("Hospital Not Found");
    }
}
