package com.yp.BloodBankApplication.Services;

import com.yp.BloodBankApplication.Entity.BloodBank;
import com.yp.BloodBankApplication.Enums.BloodGroup;
import com.yp.BloodBankApplication.Exception.BloodBankNotFoundException;
import com.yp.BloodBankApplication.Repository.BloodBankRepository;
import com.yp.BloodBankApplication.Requests.BloodBankRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
public class BloodBankService {

    @Autowired
    private BloodBankRepository bloodBankRepository;


    public BloodBank registerBloodBank(BloodBankRequest bloodBankRequest){
        List<BloodGroup> bloodGroups = Stream.of(BloodGroup.values()).toList();
        Map<BloodGroup,Integer> bloodGroupIntegerMap = new HashMap<>();
        for(BloodGroup bg : bloodGroups){
            bloodGroupIntegerMap.put(bg,0);
        }
        BloodBank bloodBank = new BloodBank(bloodBankRequest.getBloodBankId(), bloodBankRequest.getBloodBankName(), bloodBankRequest.getAddress(), bloodBankRequest.getPhoneNo(),new ArrayList<>(),bloodBankRequest.getMailAddress(),bloodGroupIntegerMap);
        return bloodBankRepository.save(bloodBank);
    }

    public BloodBank updateBloodBank(BloodBankRequest bloodBankRequest){
        BloodBank bank = bloodBankRepository.findById(bloodBankRequest.getBloodBankId()).orElse(null);
        if(bank != null){
            bank.setBloodBankName(bloodBankRequest.getBloodBankName());
            bank.setAddress(bloodBankRequest.getAddress());
            bank.setPhoneNumber(bloodBankRequest.getPhoneNo());
            bank.setMailAddress(bloodBankRequest.getMailAddress());
            return bloodBankRepository.save(bank);
        }
        throw new BloodBankNotFoundException("Blood Bank Not Found");
    }
}
