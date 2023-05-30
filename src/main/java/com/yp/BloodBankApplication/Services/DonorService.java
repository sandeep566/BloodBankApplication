package com.yp.BloodBankApplication.Services;

import com.yp.BloodBankApplication.Entity.BloodBank;
import com.yp.BloodBankApplication.Entity.Donor;
import com.yp.BloodBankApplication.Exception.BloodBankNotFoundException;
import com.yp.BloodBankApplication.Repository.BloodBankRepository;
import com.yp.BloodBankApplication.Repository.DonorRepository;
import com.yp.BloodBankApplication.Requests.DonorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class DonorService {

    @Autowired
    private DonorRepository donorRepository;

    @Autowired
    private BloodBankRepository bloodBankRepository;

    public Donor registerDonor(DonorRequest donorRequest, int bloodBankId){
        BloodBank bloodBank = bloodBankRepository.findById(bloodBankId).orElse(null);
        if(bloodBank != null){
                bloodBank.getBloodGroups()
                        .put(donorRequest.getBloodGroup(),bloodBank.getBloodGroups()
                                .get(donorRequest.getBloodGroup()) + donorRequest.getDonationQuantity());

                Donor donor = new Donor(donorRequest.getDonorId(), donorRequest.getDonorName(),donorRequest.getAge(),
                        donorRequest.getAddress(), donorRequest.getPhoneNo(),
                        donorRequest.getBloodGroup(),donorRequest.getBloodGroupList(),bloodBank,
                        donorRequest.getDonationQuantity());

                return donorRepository.save(donor);
        }else{
            throw new BloodBankNotFoundException("Blood Bank Not Found");
        }

    }
}
