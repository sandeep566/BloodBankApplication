package com.yp.BloodBankApplication.Services;

import com.yp.BloodBankApplication.Entity.BloodBank;
import com.yp.BloodBankApplication.Enums.BloodGroup;
import com.yp.BloodBankApplication.Repository.BloodBankRepository;
import com.yp.BloodBankApplication.Requests.BloodBankRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BloodBankService {

    @Autowired
    private BloodBankRepository bloodBankRepository;

    public BloodBank registerBloodBank(BloodBankRequest bloodBankRequest){
        List<BloodGroup> groups = Stream.of(BloodGroup.values()).toList();
        BloodBank bloodBank = new BloodBank(bloodBankRequest.getBloodBankId(), bloodBankRequest.getBloodBankName(), bloodBankRequest.getAddress(), bloodBankRequest.getPhoneNo(),new ArrayList<>(),bloodBankRequest.getMailAddress(),new ArrayList<>());
        return bloodBankRepository.save(bloodBank);
    }
}
